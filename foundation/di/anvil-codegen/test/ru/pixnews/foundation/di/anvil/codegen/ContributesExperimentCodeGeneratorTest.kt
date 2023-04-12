/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.foundation.di.anvil.codegen

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.compiler.internal.testing.compileAnvil
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldHaveAnnotation
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.fail
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.getElementValue
import ru.pixnews.foundation.di.anvil.codegen.util.loadClass

@TestInstance(Lifecycle.PER_CLASS)
class ContributesExperimentCodeGeneratorTest {
    private val generatedModuleName = "com.test.TestExperiment_Experiments_Module"
    private lateinit var compilationResult: KotlinCompilation.Result

    @BeforeAll
    fun setup() {
        compilationResult = compileAnvil(
            """
            package ru.pixnews.foundation.featuretoggles
            interface Experiment
            interface ExperimentVariantSerializer
            """.trimIndent(),
            """
            package ru.pixnews.foundation.featuretoggles.inject
            annotation class ExperimentVariantMapKey(val key: String)
            public annotation class ContributesExperiment
            public annotation class ContributesExperimentVariantSerializer(val experimentKey: String)
            public abstract class ExperimentScope private constructor()
            """.trimIndent(),
            """
            package com.test
            import ru.pixnews.foundation.featuretoggles.Experiment
            import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
            import ru.pixnews.foundation.featuretoggles.inject.ContributesExperiment
            import ru.pixnews.foundation.featuretoggles.inject.ContributesExperimentVariantSerializer

            @ContributesExperiment
            public object TestExperiment : Experiment {
                @ContributesExperimentVariantSerializer(experimentKey = "test.serializer")
                public object TestExperimentSerializer : ExperimentVariantSerializer

                @ContributesExperimentVariantSerializer("test.serializer.no.key")
                public object TestNoKeyExperimentSerializer : ExperimentVariantSerializer
            }
        """.trimIndent(),
        )
    }

    @Test
    fun `Dagger module should be generated`() {
        compilationResult.exitCode shouldBe OK
    }

    @Test
    fun `Generated module should have correct annotations`() {
        val clazz = compilationResult.classLoader.loadClass(generatedModuleName)
        clazz.shouldHaveAnnotation(ContributesTo::class.java)
    }

    @Test
    fun `Generated module should have correct providing method for experiment`() {
        val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
        val experimentClass = compilationResult.classLoader.loadClass(ClassNames.experiment)

        val provideMethod = moduleClass.declaredMethods.firstOrNull {
            it.name == "provideTestExperiment"
        } ?: fail("no provideTestExperiment method")

        provideMethod.returnType shouldBe experimentClass
        provideMethod.parameterTypes.shouldBeEmpty()
        provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
            Provides::class,
            IntoSet::class,
        )
    }

    @Test
    fun `Generated module should have providing method for experiment variant serializer`() {
        testExperimentVariantProvideMethod(
            methodName = "provideTestExperimentSerializer",
            experimentKey = "test.serializer",
        )
    }

    @Test
    fun `Generated module should have providing method for experiment variant serializer with no key parameter`() {
        testExperimentVariantProvideMethod(
            methodName = "provideTestNoKeyExperimentSerializer",
            experimentKey = "test.serializer.no.key",
        )
    }

    private fun testExperimentVariantProvideMethod(
        methodName: String,
        experimentKey: String,
    ) {
        val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
        val experimentVariantSerializerClass = compilationResult.classLoader.loadClass(
            ClassNames.experimentVariantSerializer,
        )

        @Suppress("UNCHECKED_CAST") val experimentVariantMapKey = compilationResult.classLoader
            .loadClass(ClassNames.experimentVariantMapKey) as Class<Annotation>

        val provideMethod = moduleClass.declaredMethods.firstOrNull { it.name == methodName }
            ?: fail("no $methodName method")

        provideMethod.returnType shouldBe experimentVariantSerializerClass
        provideMethod.parameterTypes.shouldBeEmpty()
        provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
            Provides::class,
            IntoMap::class,
            experimentVariantMapKey.kotlin,
        )
        provideMethod.getAnnotation(experimentVariantMapKey).getElementValue<String>("key") shouldBe experimentKey
    }
}

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
import dagger.MembersInjector
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldHaveAnnotation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.fail
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.loadClass
import ru.pixnews.foundation.di.base.scopes.SingleIn

@OptIn(ExperimentalCompilerApi::class)
@TestInstance(Lifecycle.PER_CLASS)
class ContributesTestCodeGeneratorTest {
    private val generatedModuleName = "com.test.MainTest_TestModule"
    private lateinit var compilationResult: KotlinCompilation.Result

    @BeforeAll
    @Suppress("LOCAL_VARIABLE_EARLY_DECLARATION")
    fun setup() {
        val testStubs = """
            package ru.pixnews.foundation.instrumented.test.di
            import dagger.MembersInjector

            annotation class ContributesTest

            @Suppress("UNUSED_PARAMETER")
            class SingleInstrumentedTestInjector(injector: MembersInjector<*>)
        """.trimIndent()

        val testClass = """
            package com.test
            import ru.pixnews.foundation.instrumented.test.di.ContributesTest

            @ContributesTest
            class MainTest
        """.trimIndent()
        compilationResult = compileAnvil(
            sources = arrayOf(
                testStubs,
                testClass,
            ),
        )
    }

    @Test
    fun `Dagger module should be generated`() {
        compilationResult.exitCode shouldBe OK
    }

    @Test
    fun `Generated module should have correct annotations`() {
        val clazz = compilationResult.classLoader.loadClass(generatedModuleName)
        val appScopeClass = compilationResult.classLoader.loadClass(ClassNames.appScope)
        clazz.shouldHaveAnnotation(ContributesTo::class.java)

        clazz.getAnnotation(ContributesTo::class.java).scope.java shouldBe appScopeClass
    }

    @Test
    fun `Generated module should have correct provide method`() {
        val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
        val singleInstrumentedTestInjectorClass = compilationResult.classLoader.loadClass(
            ClassNames.singleInstrumentedTestInjector,
        )

        val provideMethod = moduleClass.declaredMethods.firstOrNull {
            it.name == "provideMainTestInjector"
        } ?: fail("no provideMainTestInjector method")
        provideMethod.returnType shouldBe singleInstrumentedTestInjectorClass
        provideMethod.parameterTypes.shouldContainExactly(MembersInjector::class.java)
        provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
            Provides::class,
            IntoMap::class,
            SingleIn::class,
            ClassKey::class,
        )
    }
}

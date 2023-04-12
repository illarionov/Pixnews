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
import dagger.Binds
import dagger.MembersInjector
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
class ContributesActivityCodeGeneratorTest {
    private val generatedModuleName = "com.test.TestActivity_ActivityModule"
    private lateinit var compilationResult: KotlinCompilation.Result

    @BeforeAll
    @Suppress("LOCAL_VARIABLE_EARLY_DECLARATION")
    fun setup() {
        val activityDiStubs = """
            package ru.pixnews.foundation.di.ui.base.activity
            import android.app.Activity
            import dagger.MapKey
            import kotlin.reflect.KClass

            public abstract class ActivityScope private constructor()
            public annotation class ActivityMapKey(val activityClass: KClass<out Activity>)
            public annotation class ContributesActivity
        """.trimIndent()

        val androidActivityStub = """
            package android.app
            open class Activity
        """.trimIndent()

        val testActivity = """
            package com.test

            import android.app.Activity
            import ru.pixnews.foundation.di.ui.base.activity.ContributesActivity

            @ContributesActivity
            class TestActivity : Activity()
        """.trimIndent()

        compilationResult = compileAnvil(
            sources = arrayOf(
                activityDiStubs,
                androidActivityStub,
                testActivity,
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
        val activityScopeClass = compilationResult.classLoader.loadClass(ClassNames.activityScope)
        clazz.shouldHaveAnnotation(ContributesTo::class.java)

        clazz.getAnnotation(ContributesTo::class.java).scope.java shouldBe activityScopeClass
    }

    @Test
    fun `Generated module should have correct binding method`() {
        val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
        val activityMapKey = compilationResult.classLoader.loadClass(ClassNames.activityMapKey)

        val provideMethod = moduleClass.declaredMethods.firstOrNull {
            it.name == "bindsTestActivityInjector"
        } ?: fail("no bindsTestActivityInjector method")

        provideMethod.returnType shouldBe MembersInjector::class.java
        provideMethod.parameterTypes.shouldContainExactly(MembersInjector::class.java)
        provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
            Binds::class,
            IntoMap::class,
            SingleIn::class,
            activityMapKey.kotlin,
        )
    }
}

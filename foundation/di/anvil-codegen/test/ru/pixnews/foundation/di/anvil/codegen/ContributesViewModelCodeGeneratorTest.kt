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
import com.squareup.kotlinpoet.ClassName
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import dagger.Provides
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

@OptIn(ExperimentalCompilerApi::class)
@TestInstance(Lifecycle.PER_CLASS)
class ContributesViewModelCodeGeneratorTest {
    private val generatedModuleName = "com.test.TestViewModel_FactoryModule"
    private val featureManagerClass = ClassName("ru.pixnews.foundation.featuretoggles", "FeatureManager")
    private lateinit var compilationResult: KotlinCompilation.Result

    @BeforeAll
    @Suppress("LOCAL_VARIABLE_EARLY_DECLARATION")
    fun setup() {
        val androidxLifecycleStubs = """
            package androidx.lifecycle

            abstract class ViewModel
            class SavedStateHandle
            abstract class CreationExtras

            fun CreationExtras.createSavedStateHandle(): SavedStateHandle = SavedStateHandle()
        """.trimIndent()

        val featureTogglesStubs = """
            package ${featureManagerClass.packageName}
            interface ${featureManagerClass.simpleName}
        """.trimIndent()

        val baseDiViewModelStubs = """
            package ru.pixnews.foundation.di.ui.base.viewmodel

            import androidx.lifecycle.ViewModel
            import androidx.lifecycle.CreationExtras
            import kotlin.reflect.KClass

            public annotation class ContributesViewModel
            public annotation class ViewModelMapKey(val viewModelClass: KClass<out ViewModel>)
            public abstract class ViewModelScope private constructor()

            public fun interface ViewModelFactory {
                public fun create(creationExtras: CreationExtras): ViewModel
            }
        """.trimIndent()

        val testViewModel = """
            package com.test

            import androidx.lifecycle.ViewModel
            import androidx.lifecycle.SavedStateHandle
            import ru.pixnews.foundation.featuretoggles.FeatureManager
            import ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel

            @Suppress("UNUSED_PARAMETER")
            @ContributesViewModel
            class TestViewModel(
                featureManager: FeatureManager,
                savedStateHandle: SavedStateHandle,
            ) : ViewModel()
        """.trimIndent()

        compilationResult = compileAnvil(
            sources = arrayOf(
                baseDiViewModelStubs,
                androidxLifecycleStubs,
                featureTogglesStubs,
                testViewModel,
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
        val viewModelScopeClass = compilationResult.classLoader.loadClass(ClassNames.viewModelScope)
        clazz.shouldHaveAnnotation(ContributesTo::class.java)

        clazz.getAnnotation(ContributesTo::class.java).scope.java shouldBe viewModelScopeClass
    }

    @Test
    fun `Generated module should have correct provide method`() {
        val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
        val viewModelMapKey = compilationResult.classLoader.loadClass(ClassNames.viewModelMapKey)
        val viewModelFactoryClass = compilationResult.classLoader.loadClass(ClassNames.viewModelFactory)
        val featureManagerClass = compilationResult.classLoader.loadClass(featureManagerClass)

        val provideMethod = moduleClass.declaredMethods.firstOrNull {
            it.name == "providesTestViewModelViewModelFactory"
        } ?: fail("no providesTestViewModelViewModelFactory method")

        provideMethod.returnType shouldBe viewModelFactoryClass
        provideMethod.parameterTypes.shouldContainExactly(featureManagerClass)
        provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
            Provides::class,
            IntoMap::class,
            viewModelMapKey.kotlin,
        )
    }
}

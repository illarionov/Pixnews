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

import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.anvil.compiler.internal.testing.compileAnvil
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import dagger.assisted.AssistedFactory
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldHaveAnnotation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.fail
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.getElementValue
import ru.pixnews.foundation.di.anvil.codegen.util.loadClass

@OptIn(ExperimentalCompilerApi::class)
@TestInstance(Lifecycle.PER_CLASS)
class ContributesCoroutineWorkerCodeGeneratorTest {
    private val generatedFactoryName = "com.test.TestWorker_AssistedFactory"
    private lateinit var compilationResult: KotlinCompilation.Result

    @BeforeAll
    @Suppress("LOCAL_VARIABLE_EARLY_DECLARATION", "LongMethod")
    fun setup() {
        val androidContextStub = """
            package android.content
            open class Context
        """.trimIndent()

        val workManagerStubs = """
            package androidx.work
            import android.content.Context

            open class WorkerParameters

            @Suppress("UNUSED_PARAMETER")
            abstract class CoroutineWorker(
                appContext: Context,
                params: WorkerParameters
            ) {
              public abstract suspend fun doWork(): Result<Unit>
            }
        """.trimIndent()

        val daggerStubs = """
            package dagger.assisted
            public annotation class Assisted
            public annotation class AssistedInject
        """.trimIndent()

        val appContextQualifier = """
            package ru.pixnews.foundation.di.base.qualifiers
            public annotation class ApplicationContext
        """.trimIndent()

        val workmanagerDiStubs = """
            package ru.pixnews.foundation.di.workmanager
            import android.content.Context
            import androidx.work.CoroutineWorker
            import androidx.work.WorkerParameters
            import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
            import kotlin.reflect.KClass

            public abstract class WorkManagerScope private constructor()
            public annotation class ContributesCoroutineWorker
            public annotation class CoroutineWorkerMapKey(val workerClass: KClass<out CoroutineWorker>)

            public interface CoroutineWorkerFactory {
                public fun create(
                    @ApplicationContext context: Context,
                    workerParameters: WorkerParameters,
                ): CoroutineWorker
            }
        """.trimIndent()

        val testWorker = """
            package com.test

            import android.content.Context
            import androidx.work.CoroutineWorker
            import androidx.work.WorkerParameters
            import dagger.assisted.Assisted
            import dagger.assisted.AssistedInject
            import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
            import ru.pixnews.foundation.di.workmanager.ContributesCoroutineWorker

            @Suppress("UNUSED_PARAMETER")
            @ContributesCoroutineWorker
            public class TestWorker @AssistedInject constructor(
                @Assisted @ApplicationContext appContext: Context,
                @Assisted params: WorkerParameters,
            ) : CoroutineWorker(appContext, params) {
                override suspend fun doWork(): Result<Unit> {
                    return Result.success(Unit)
                }
            }
        """.trimIndent()

        compilationResult = compileAnvil(
            sources = arrayOf(
                androidContextStub,
                workManagerStubs,
                daggerStubs,
                appContextQualifier,
                workmanagerDiStubs,
                testWorker,
            ),
        )
    }

    @Test
    fun `Dagger factory should be generated`() {
        compilationResult.exitCode shouldBe OK
    }

    @Test
    fun `Generated factory should have correct annotations and superclass`() {
        val clazz = compilationResult.classLoader.loadClass(generatedFactoryName)
        val testWorkerClass = compilationResult.classLoader.loadClass("com.test.TestWorker")
        val workManagerScopeClass = compilationResult.classLoader.loadClass(ClassNames.workManagerScope)
        val coroutineWorkerFactoryClass = compilationResult.classLoader.loadClass(ClassNames.coroutineWorkerFactory)

        @Suppress("UNCHECKED_CAST")
        val coroutineWorkerMapKeyClass: Class<Annotation> = compilationResult.classLoader.loadClass(
            ClassNames.coroutineWorkerMapKey,
        ) as Class<Annotation>

        clazz.shouldHaveAnnotation(AssistedFactory::class.java)
        clazz.shouldHaveAnnotation(ContributesMultibinding::class.java)
        clazz.getAnnotation(ContributesMultibinding::class.java).scope.java shouldBe workManagerScopeClass

        clazz.getAnnotation(coroutineWorkerMapKeyClass).getElementValue<Class<*>>("workerClass")
            .shouldBe(testWorkerClass)

        assertTrue { coroutineWorkerFactoryClass.isAssignableFrom(clazz) }
    }

    @Test
    fun `Generated factory should have correct create method`() {
        val factoryClass = compilationResult.classLoader.loadClass(generatedFactoryName)
        val testWorkerClass = compilationResult.classLoader.loadClass("com.test.TestWorker")
        val androidContextClass = compilationResult.classLoader.loadClass(ClassNames.Android.context)
        val workerParamsClass = compilationResult.classLoader.loadClass(ClassNames.Android.workerParameters)

        val createMethod = factoryClass.declaredMethods.firstOrNull {
            it.name == "create"
        } ?: fail("no create() method")

        createMethod.parameterTypes.shouldContainExactly(androidContextClass, workerParamsClass)
        createMethod.returnType shouldBe testWorkerClass
    }
}

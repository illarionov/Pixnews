/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.anvil.codegen

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.compiler.internal.testing.compileAnvil
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldHaveAnnotation
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.fail
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.loadClass
import javax.inject.Provider

@TestInstance(Lifecycle.PER_CLASS)
class ContributesInitializerCodeGeneratorTest {
    private val initializerInterfacesCode = """
        package ru.pixnews.foundation.initializers

        public fun interface Initializer { public fun init() }
        public fun interface AsyncInitializer { public fun init() }
    """.trimIndent()
    private val initializersInjectCode = """
        package ru.pixnews.foundation.initializers.inject
        import kotlin.reflect.KClass
        public abstract class AppInitializersScope private constructor()
        public annotation class ContributesInitializer(
            val replaces: Array<KClass<*>> = [],
        )
    """.trimIndent()
    private val loggerStubCode = """
        package co.touchlab.kermit
        open class Logger
    """.trimIndent()
    private val firebaseStubCode = """
        package com.google.firebase;
        open class FirebaseApp
    """.trimIndent()
    private val javaxInjectProviderCode = """
        package javax.inject;
        public interface Provider<T> {
            fun get(): T
        }
    """.trimIndent()
    private val firebaseInitializerCode = """
        package ru.pixnews.initializers
        import co.touchlab.kermit.Logger
        import com.google.firebase.FirebaseApp
        import ru.pixnews.foundation.initializers.AsyncInitializer
        import ru.pixnews.foundation.initializers.inject.ContributesInitializer
        import javax.inject.Provider

        @ContributesInitializer
        public class FirebaseInitializer(
            private val logger: Logger,
            private val firebaseApp: Provider<@JvmSuppressWildcards FirebaseApp>,
        ) : AsyncInitializer {
            override fun init() {
            }
        }
    """.trimIndent()
    private val testFirebaseInitializerCode = """
        package ru.pixnews.test.initializers
        import co.touchlab.kermit.Logger
        import com.google.firebase.FirebaseApp
        import ru.pixnews.foundation.initializers.AsyncInitializer
        import ru.pixnews.foundation.initializers.inject.ContributesInitializer
        import ru.pixnews.initializers.FirebaseInitializer
        import javax.inject.Provider

        @ContributesInitializer(replaces = [FirebaseInitializer::class])
        public class TestFirebaseInitializer(
            private val logger: Logger,
        ) : AsyncInitializer {
            override fun init() {
            }
        }
    """.trimIndent()
    private lateinit var compilationResult: JvmCompilationResult

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    inner class TestInitializer {
        private val generatedModuleName = "ru.pixnews.initializers.FirebaseInitializer_InitializerModule"

        @BeforeAll
        fun setup() {
            compilationResult = compileAnvil(
                initializersInjectCode,
                firebaseStubCode,
                initializerInterfacesCode,
                javaxInjectProviderCode,
                loggerStubCode,
                firebaseInitializerCode,
            )
        }

        @Test
        fun `Dagger module should be generated`() {
            compilationResult.exitCode shouldBe OK
        }

        @Test
        fun `Generated module should have correct annotations`() {
            val clazz = compilationResult.classLoader.loadClass(generatedModuleName)
            val appInitializerScopeClass = compilationResult.classLoader.loadClass(ClassNames.appInitializersScope)

            clazz.shouldHaveAnnotation(ContributesTo::class.java)
            clazz.shouldHaveAnnotation(Module::class.java)

            clazz.getAnnotation(ContributesTo::class.java).scope.java shouldBe (appInitializerScopeClass)
        }

        @Test
        fun `Generated module should have correct provide method`() {
            val moduleClass = compilationResult.classLoader.loadClass(generatedModuleName)
            val asyncInitializerClass = compilationResult.classLoader.loadClass(ClassNames.asyncInitializer)
            val loggerClass = compilationResult.classLoader.loadClass("co.touchlab.kermit.Logger")

            val provideMethod = moduleClass.declaredMethods.firstOrNull {
                it.name == "provideFirebaseInitializer"
            } ?: fail("no provideFirebaseInitializer method")

            provideMethod.returnType shouldBe asyncInitializerClass
            provideMethod.annotations.map(Annotation::annotationClass) shouldContainExactlyInAnyOrder listOf(
                Provides::class,
                IntoSet::class,
                Reusable::class,
            )
            provideMethod.parameterTypes.shouldContainExactly(
                loggerClass,
                Provider::class.java,
            )
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    inner class TestReplaces {
        private val generatedModuleName = "ru.pixnews.test.initializers.TestFirebaseInitializer_InitializerModule"

        @BeforeAll
        fun setup() {
            compilationResult = compileAnvil(
                initializersInjectCode,
                firebaseStubCode,
                initializerInterfacesCode,
                javaxInjectProviderCode,
                loggerStubCode,
                firebaseInitializerCode,
                testFirebaseInitializerCode,
            )
        }

        @Test
        fun `Generated module should have correct annotations`() {
            val clazz = compilationResult.classLoader.loadClass(generatedModuleName)
            val appInitializerScopeClass = compilationResult.classLoader.loadClass(ClassNames.appInitializersScope)
            val replacesScopeClass = compilationResult.classLoader.loadClass(
                "ru.pixnews.initializers.FirebaseInitializer_InitializerModule",
            )

            clazz.shouldHaveAnnotation(ContributesTo::class.java)
            clazz.shouldHaveAnnotation(Module::class.java)

            clazz.getAnnotation(ContributesTo::class.java).scope.java shouldBe (appInitializerScopeClass)
            clazz.getAnnotation(ContributesTo::class.java).replaces
                .map { it.java }
                .shouldContainExactly(replacesScopeClass)
        }
    }
}

/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.kotlin

import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import ru.pixnews.gradle.project.base.createPixnewsExtension
import ru.pixnews.gradle.project.base.pixnews
import ru.pixnews.gradle.project.testing.configureCommonUnitTesting
import ru.pixnews.gradle.project.testing.configureCommonUnitTestingOptions

/**
 * Convention plugin for use in kotlin only modules
 */
plugins {
    kotlin("jvm")
    id("ru.pixnews.gradle.base.build-parameters")
    id("ru.pixnews.gradle.project.lint.android-lint-noagp")
}

createPixnewsExtension()

kotlin {
    explicitApi = ExplicitApiMode.Warning
    sourceSets {
        main {
            kotlin.setSrcDirs(listOf("main"))
            resources.setSrcDirs(listOf("main_resources"))
        }
        test {
            kotlin.setSrcDirs(listOf("test"))
            resources.setSrcDirs(listOf("test_resources"))
        }
    }
}

plugins.withId("java-test-fixtures") {
    kotlin.sourceSets.getByName("testFixtures") {
        kotlin.setSrcDirs(listOf("testFixtures"))
        resources.setSrcDirs(listOf("testFixtures_resources"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
    .configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            // D8: An error occurred when parsing kotlin metadata.
            // languageVersion.set(KOTLIN_2_0)
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
                "-Xjvm-default=all",
            )
            if (this@configureEach.name.endsWith("TestFixturesKotlin")) {
                freeCompilerArgs.addAll("-Xexplicit-api=warning")
            }
        }
    }

afterEvaluate {
    val unitTestEngine = project.pixnews.unitTestEngine.get()
    configureCommonUnitTesting(unitTestEngine)
    tasks.withType<Test>().configureEach {
        configureCommonUnitTestingOptions(unitTestEngine)
    }
}

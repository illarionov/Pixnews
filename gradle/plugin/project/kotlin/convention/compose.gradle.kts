/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.kotlin

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that enables Compose
 */
plugins {
    id("ru.pixnews.gradle.base.build-parameters")
    id("org.jetbrains.kotlin.plugin.compose")
}

composeCompiler {
    enableStrongSkippingMode = true
    if (project.buildParameters.compose.enable_compose_compiler_metrics) {
        metricsDestination = project.layout.buildDirectory.dir("compose-metrics")
    }
    if (project.buildParameters.compose.enable_compose_compiler_metrics) {
        reportsDestination = project.layout.buildDirectory.dir("compose-reports")
    }
}

afterEvaluate {
    val composeBom = versionCatalog.findLibrary("androidx-compose-bom").orElseThrow()
    val composeRuntime = versionCatalog.findLibrary("androidx-compose-runtime").orElseThrow()
    dependencies {
        add("implementation", platform(composeBom))
        add("implementation", composeRuntime) // TODO: should be compileOnly?
    }

    plugins.withType<JavaTestFixturesPlugin> {
        dependencies {
            add("testFixturesImplementation", composeRuntime) // TODO: should be compileOnly?
            add("testFixturesImplementation", platform(composeBom)) // TODO: should be compileOnly?
        }
    }
}

tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>()
    .configureEach {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-opt-in=androidx.compose.ui.text.ExperimentalTextApi",
            )
        }
    }

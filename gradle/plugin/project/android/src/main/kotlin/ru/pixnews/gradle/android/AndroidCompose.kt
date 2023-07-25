/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import ru.pixnews.gradle.base.compose.buildComposeMetricsParameters
import ru.pixnews.gradle.base.versionCatalog

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versionCatalog.findVersion("androidx-compose-compiler").get().toString()
        }
    }

    tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>()
        .matching { it !is KaptGenerateStubsTask }
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

    configureComposeMetrics()
}

private fun Project.configureComposeMetrics() {
    val metricsParams = buildComposeMetricsParameters()
    if (metricsParams.isNotEmpty()) {
        tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .matching { it !is KaptGenerateStubsTask }
            .configureEach {
                compilerOptions {
                    freeCompilerArgs.addAll(metricsParams)
                }
            }
    }
}

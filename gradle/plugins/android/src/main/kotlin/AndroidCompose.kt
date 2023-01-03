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
package ru.pixnews

import buildparameters.BuildParametersExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

private val Project.buildParameters: BuildParametersExtension get() = extensions.getByType()

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versionCatalog.findVersion("androidx-compose-compiler").get().toString()
        }

        dependencies {
            val bom = versionCatalog.findLibrary("androidx-compose-bom").orElseThrow()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
        .configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.0",
                )
            }
        }

    configureComposeMetrics()
}

private fun Project.configureComposeMetrics() {
    val metricsParams = buildComposeMetricsParameters()
    if (metricsParams.isNotEmpty()) {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .configureEach {
                compilerOptions {
                    freeCompilerArgs.addAll(metricsParams)
                }
            }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters: MutableList<String> = mutableListOf()
    val enableMetrics = project.buildParameters.compose.enable_compose_compiler_metrics
    if (enableMetrics) {
        val metricsFolder = project.layout.buildDirectory.dir("compose-metrics").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${metricsFolder.absolutePath}",
        )
    }

    val enableReports = project.buildParameters.compose.enable_compose_compiler_reports
    if (enableReports) {
        val reportsFolder = project.layout.buildDirectory.dir("compose-reports").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${reportsFolder.absolutePath}",
        )
    }
    return metricParameters
}

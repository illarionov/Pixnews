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

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import ru.pixnews.compose.buildComposeMetricsParameters

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        @Suppress("MagicNumber")
        if (this is LibraryExtension) {
            // https://issuetracker.google.com/u/1/issues/267458965
            defaultConfig.targetSdk = versionCatalog.findVersion("targetSdk").get().displayName.toInt()
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versionCatalog.findVersion("androidx-compose-compiler").get().toString()
        }

        dependencies {
            val bom = versionCatalog.findLibrary("androidx-compose-bom").orElseThrow()
            add("implementation", platform(bom))
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

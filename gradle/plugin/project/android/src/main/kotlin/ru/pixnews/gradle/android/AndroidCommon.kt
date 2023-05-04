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
@file:Suppress("MagicNumber", "MaxLineLength")

package ru.pixnews.gradle.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import ru.pixnews.gradle.base.versionCatalog

internal fun Project.configureCommonAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = versionCatalog.findVersion("compileSdk").get().displayName.toInt()
        namespace = "ru.pixnews"

        defaultConfig {
            minSdk = versionCatalog.findVersion("minSdk").get().displayName.toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            resourceConfigurations.addAll(listOf("en", "ru"))
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            // https://issuetracker.google.com/u/1/issues/266687543
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                    // There are some plugins incompatible with K2 compiler:
                    // ComposeCompiler, R8
                    // languageVersion.set(KOTLIN_2_0)
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-opt-in=kotlinx.coroutines.FlowPreview",
                        // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
                        "-Xjvm-default=all",
                    )
                }
            }

        testOptions {
            animationsDisabled = true
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    dependencies {
        add(
            "coreLibraryDesugaring",
            versionCatalog.findLibrary("android.desugar.jdk.libs").orElseThrow(),
        )
    }
}

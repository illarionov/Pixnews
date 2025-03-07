/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("MagicNumber", "MaxLineLength")

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import ru.pixnews.gradle.project.base.versionCatalog

internal fun Project.configureCommonAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = versionCatalog.findVersion("compileSdk").get().displayName.toInt()
        namespace = "ru.pixnews"

        defaultConfig {
            minSdk = versionCatalog.findVersion("minSdk").get().displayName.toInt()

            resourceConfigurations.addAll(listOf("en", "ru"))
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
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

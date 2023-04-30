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
@file:Suppress("UnstableApiUsage", "MagicNumber")

package ru.pixnews.gradle.android

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestedExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import ru.pixnews.gradle.android.agp.workarounds.AndroidGradlePluginWorkarounds
import ru.pixnews.gradle.base.pixnews
import ru.pixnews.gradle.base.versionCatalog

internal fun Project.configureAndroidInstrumentedTests(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig {
            testInstrumentationRunner = "ru.pixnews.foundation.instrumented.test.PixnewsTestRunner"
        }
    }
    if (pixnews.compose.get()) {
        tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .matching { it.name.endsWith("AndroidTestKotlin") }
            .configureEach {
                compilerOptions.freeCompilerArgs.addAll(
                    "-opt-in=androidx.compose.ui.test.ExperimentalTestApi",
                )
            }
    }

    configureTestManagedDevices(commonExtension)
    configureAndroidTestDependencies(commonExtension)
}

private fun Project.configureTestManagedDevices(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.testOptions.managedDevices {
        val pixel5api33 = devices.maybeCreate<ManagedVirtualDevice>("pixel5api33").apply {
            device = "Pixel 5"
            apiLevel = 33
            systemImageSource = "google"
        }
        val pixel2api30 = devices.maybeCreate<ManagedVirtualDevice>("pixel2api30").apply {
            device = "Pixel 2"
            apiLevel = 30
            systemImageSource = "aosp-atd"
        }
        groups {
            maybeCreate("phone").apply {
                targetDevices += listOf(pixel5api33, pixel2api30)
            }
        }
    }
    // https://issuetracker.google.com/issues/262270582
    with(AndroidGradlePluginWorkarounds) {
        initializeManagedDeviceTestEnvironment()
    }
}

private fun Project.configureAndroidTestDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    if (commonExtension !is TestedExtension) {
        return
    }

    dependencies {
        if (pixnews.compose.get()) {
            add("debugImplementation", versionCatalog.findLibrary("androidx-compose-ui-testManifest").orElseThrow())
        }
        add("androidTestRuntimeOnly", versionCatalog.findLibrary("androidx-test-runner").orElseThrow())
        add("androidTestImplementation", project(":foundation:instrumented-test"))
    }

    plugins.withId("ru.pixnews.gradle.di.anvil-kapt") {
        dependencies {
            add("kaptAndroidTest", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
        }
    }
}

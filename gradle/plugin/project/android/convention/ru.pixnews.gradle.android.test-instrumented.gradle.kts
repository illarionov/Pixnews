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

@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestedExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.TestPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import ru.pixnews.gradle.android.agp.workarounds.AndroidGradlePluginWorkarounds
import ru.pixnews.gradle.base.pixnews
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures instrumental tests for all of android plugins.
 * Used with one of these plugins:
 * `ru.pixnews.gradle.android.application.gradle.kts`,
 * `ru.pixnews.gradle.android.library.gradle.kts`,
 * or `ru.pixnews.gradle.android.test.gradle.kts`
 */
listOf(
    AppPlugin::class.java,
    LibraryPlugin::class.java,
    DynamicFeaturePlugin::class.java,
    TestPlugin::class.java,
).forEach { agpPluginType ->
    plugins.withType(agpPluginType) {
        extensions.configure<CommonExtension<*, *, *, *, *>>("android") {
            defaultConfig {
                testInstrumentationRunner = "ru.pixnews.foundation.instrumented.test.PixnewsTestRunner"
            }
            configureTestManagedDevices(this)
            if (this is TestedExtension) {
                configureAndroidTestDependencies()
            }
        }
        extensions.configure(AndroidComponentsExtension::class.java) {
            onVariants(selector().withName("androidTest")) { variant ->
                variant.manifestPlaceholders.put("firebase_crashlytics_collection_enabled", "false")
            }
            finalizeDsl {
                if (pixnews.compose.get()) {
                    configureComposeInstrumentedTestSpecifics()
                }
            }
        }
    }
}

plugins.withType<LibraryPlugin> {
    extensions.configure(AndroidComponentsExtension::class.java) {
        beforeVariants(selector().withBuildType("debug")) { builder ->
            builder.enable = true
        }
    }
}

fun configureTestManagedDevices(
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

fun configureAndroidTestDependencies() {
    dependencies {
        val bom = platform("ru.pixnews.gradle.base:gradle-billofmaterials")
        add("androidTestImplementation", bom)
        add("androidTestImplementation", project(":foundation:instrumented-test"))
        add("androidTestRuntimeOnly", versionCatalog.findLibrary("androidx-test-runner").orElseThrow())

        constraints {
            listOf(
                "org.jetbrains.kotlinx:kotlinx-coroutines-test",
                "org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm",
            ).forEach { testDependency ->
                add("androidTestImplementation", testDependency) {
                    version {
                        strictly("1.6.4")
                        reject("1.7.0")
                        because("https://github.com/Kotlin/kotlinx.coroutines/issues/3673")
                    }
                }
            }
        }
    }

    plugins.withId("ru.pixnews.gradle.di.anvil-kapt") {
        dependencies {
            add("kaptAndroidTest", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
        }
    }
}

fun configureComposeInstrumentedTestSpecifics() {
    tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>()
        .matching { it.name.endsWith("AndroidTestKotlin") }
        .configureEach {
            compilerOptions.freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.ui.test.ExperimentalTestApi",
            )
        }
    dependencies {
        add("debugImplementation", versionCatalog.findLibrary("androidx-compose-ui-testManifest").get())
    }
}

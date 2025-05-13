/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("UnstableApiUsage")

package ru.pixnews.gradle.project.android

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
import ru.pixnews.gradle.project.base.versionCatalog

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
        extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
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
                pluginManager.withPlugin("ru.pixnews.gradle.project.kotlin.compose") {
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
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.testOptions.managedDevices {
        val pixel5api33 = allDevices.maybeCreate<ManagedVirtualDevice>("pixel5api33").apply {
            device = "Pixel 5"
            apiLevel = 33
            systemImageSource = "google"
        }
        val pixel2api30 = allDevices.maybeCreate<ManagedVirtualDevice>("pixel2api30").apply {
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
}

fun configureAndroidTestDependencies() {
    dependencies {
        add("androidTestImplementation", project(":foundation:instrumented-test"))
        add("androidTestRuntimeOnly", versionCatalog.findLibrary("androidx-test-runner").orElseThrow())
        pluginManager.withPlugin("ru.pixnews.gradle.project.kotlin.compose") {
            val composeBom = platform(versionCatalog.findLibrary("androidx.compose.bom").orElseThrow())
            add("androidTestImplementation", composeBom)
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

/*
 * Copyright (c) 2023-2025, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

pluginManagement {
    includeBuild("gradle/plugin/settings")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
    id("ru.pixnews.gradle.settings.root")
}

// Workaround for https://github.com/gradle/gradle/issues/26020
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.compose.screenshot:com.android.compose.screenshot.gradle.plugin:0.0.1-alpha09")
        classpath("com.android.tools.build:gradle:8.8.2")
        classpath("dev.zacsweers.anvil:gradle-plugin:0.4.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.3")
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.1.20")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.20-1.0.32")
        classpath("com.squareup.wire:wire-gradle-plugin:5.3.1")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:7.0.2")
        classpath("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
    }
}

rootProject.name = "Pixnews"
rootProject.buildFileName = "pixnews.gradle.kts"

includeSubproject(":app")
includeSubproject(":test:app-mock")
includeSubproject(":test:benchmark")

listOf(
    "analytics",
    "appconfig",
    "database",
    "di:base",
    "di:root-component",
    "di:ui-base",
    "di:workmanager",
    "coroutines",
    "featuretoggles:public",
    "featuretoggles:internal",
    "featuretoggles:datasource-firebase",
    "featuretoggles:datasource-overrides",
    "network:public",
    "initializers",
    "instrumented-test",
    "domain-model",
    "redux",
    "ui:design",
    "ui:design-test-constants",
    "ui:assets-icons",
    "ui:imageloader:coil",
    "ui:imageloader:coil-test",
    "ui:theme",
).forEach {
    includeSubproject(":foundation:$it")
}

listOf(
    "android-utils",
    "compose-utils",
    "coroutines",
    "functional",
    "instrumented-test",
    "internationalization",
    "kotlin-datetime-utils",
    "kotlin-utils",
    "test",
    "ui-tooling",
).forEach {
    includeSubproject(":library:$it")
}

listOf(
    "calendar:public",
    "calendar:data",
    "calendar:data-public",
    "calendar:datasource-igdb",
    "calendar:test",
    "calendar:test-constants",
    "collections:public",
    "collections:test-constants",
    "featuretoggles:public",
    "profile:public",
    "profile:test-constants",
    "root:public",
    "root:test-constants",
).forEach {
    includeSubproject(":feature:$it")
}

fun includeSubproject(projectPath: String, buildFileName: String? = null) {
    include(projectPath)

    val newBuildFileName = buildFileName ?: (
            projectPath
                .replace("""^:(?:feature|library|foundation|test)""".toRegex(), "")
                .removePrefix(":")
                .replace("""[:_-]+""".toRegex(), "-") + ".gradle.kts"
            )
    project(projectPath).buildFileName = newBuildFileName
}

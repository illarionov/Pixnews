/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
pluginManagement {
    includeBuild("gradle/plugin/settings")
}

plugins {
    id("ru.pixnews.gradle.settings.root")
}

// Workaround for https://github.com/gradle/gradle/issues/26020
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.compose.screenshot:com.android.compose.screenshot.gradle.plugin:0.0.1-alpha01")
        classpath("com.android.tools.build:gradle:8.6.0-alpha01")
        classpath("com.squareup.anvil:gradle-plugin:2.5.0-beta09")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.24-1.0.20")
        classpath("com.squareup.wire:wire-gradle-plugin:4.9.9")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
        classpath("androidx.room:androidx.room.gradle.plugin:2.6.1")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
        classpath("com.saveourtool.diktat:diktat-gradle-plugin:2.0.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
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

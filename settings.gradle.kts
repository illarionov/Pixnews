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

rootProject.name = "Pixnews"
rootProject.buildFileName = "pixnews.gradle.kts"

includeSubproject(":app")
includeSubproject(":test:benchmark")

listOf(
    "analytics",
    "appconfig",
    "di:anvil-codegen",
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
    "kotlin-utils",
    "test",
    "ui-tooling",
).forEach {
    includeSubproject(":library:$it")
}

listOf(
    "calendar:public",
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

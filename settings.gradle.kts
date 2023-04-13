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
    "di:instrumented-test",
    "di:ui-base",
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
    "kotlin-utils",
    "ui-tooling",
    "test",
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

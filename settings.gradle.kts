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
    includeBuild("gradle/base-kotlin-dsl-plugin")
    includeBuild("gradle/meta-plugins")
}

plugins {
    id("ru.pixnews.settings")
}

rootProject.name = "Pixnews"

include(":app", ":test:benchmark")

listOf(
    "analytics",
    "appconfig",
    "di:base",
    "di:root-component",
    "di:instrumented-testing",
    "di:ui-base",
    "dispatchers",
    "featuretoggles:public",
    "featuretoggles:internal",
    "featuretoggles:datasource-firebase",
    "featuretoggles:datasource-overrides",
    "network:public",
    "initializers",
    "instrumented-testing",
    "domain-model",
    "redux",
    "ui:design",
    "ui:assets-icons",
    "ui:imageloader:coil",
    "ui:imageloader:coil-test",
    "ui:theme",
).forEach {
    include(":foundation:$it")
}

listOf(
    "android-utils",
    "compose-utils",
    "coroutines",
    "functional",
    "kotlin-utils",
    "ui-tooling",
    "testing",
).forEach {
    include(":library:$it")
}

listOf(
    "calendar",
    "calendar-testing",
    "collections",
    "featuretoggles",
    "profile",
    "root",
).forEach {
    include(":features:$it")
}

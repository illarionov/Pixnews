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
    includeBuild("../base/kotlindsl")
    includeBuild("../settings")
}

plugins {
    id("ru.pixnews.gradle.settings.root")
}

dependencyResolutionManagement {
    includeBuild("../base/build-parameters")
    versionCatalogs {
        create("libs") {
            from(files("../../libs.versions.toml"))
        }
    }
}

includeSubproject("base")
includeSubproject("android")
includeSubproject("di")
includeSubproject("kotlin")
includeSubproject("lint")
includeSubproject("protobuf")
includeSubproject("testing")

fun includeSubproject(path: String) {
    include(path)
    project(":$path").buildFileName = "project-plugin-$path.gradle.kts"
}

rootProject.name = "gradle-project-plugins"
rootProject.buildFileName = "project-plugins.gradle.kts"

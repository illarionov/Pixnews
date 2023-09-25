/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
includeSubproject("config")
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

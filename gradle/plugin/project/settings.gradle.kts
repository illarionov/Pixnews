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

// Workaround for https://github.com/gradle/gradle/issues/26020
buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$embeddedKotlinVersion")
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
includeSubproject("testing-graalvm")

fun includeSubproject(path: String) {
    include(path)
    project(":$path").buildFileName = "project-plugin-$path.gradle.kts"
}

rootProject.name = "gradle-project-plugins-pixnews"
rootProject.buildFileName = "project-plugins.gradle.kts"

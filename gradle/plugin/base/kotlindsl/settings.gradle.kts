/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories.gradlePluginPortal()
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

rootProject.name = "gradle-kotlindsl"
rootProject.buildFileName = "kotlindsl.gradle.kts"

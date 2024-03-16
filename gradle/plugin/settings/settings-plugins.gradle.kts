/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.base.kotlindsl")
}

group = "ru.pixnews.gradle.settings"

dependencies {
    // noinspection UseTomlInstead
    implementation("com.gradle:gradle-enterprise-gradle-plugin:3.17.2")
    implementation("org.gradle.toolchains:foojay-resolver:0.8.0")
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

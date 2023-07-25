/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.pixnews.gradle.testing"

dependencies {
    implementation(platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
    implementation("ru.pixnews.gradle.base:gradle-build-parameters")
    implementation(gradleKotlinDsl())
    implementation(projects.base)
}

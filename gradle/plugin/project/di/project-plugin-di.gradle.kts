/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.base.kotlindsl")
}

group = "ru.pixnews.gradle.di"

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
    implementation(projects.base)
    implementation(libs.anvil.plugin)
    implementation(libs.kotlin.jvm.plugin)
}

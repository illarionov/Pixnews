/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.base.kotlindsl")
}

group = "ru.pixnews.gradle.lint"

dependencies {
    implementation(projects.base)
    implementation(libs.agp.plugin.api)
    runtimeOnly(libs.agp.plugin)
    implementation(libs.detekt.plugin)
    implementation(libs.diktat.plugin)
    implementation(libs.spotless.plugin)
}

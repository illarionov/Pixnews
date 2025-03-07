/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.base.kotlindsl")
}

group = "ru.pixnews.gradle"

dependencies {
    implementation("ru.pixnews.gradle.base:gradle-build-parameters")

    implementation(projects.base)
    implementation(projects.di)
    implementation(projects.kotlin)
    implementation(projects.lint)
    implementation(projects.testing)

    implementation(libs.agp.plugin.api)
    implementation(libs.android.compose.screenshot.plugin)
    implementation(libs.androidx.room.plugin)
    runtimeOnly(libs.agp.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.ksp.plugin)
    implementation(libs.firebase.crashlitycs.plugin)
}

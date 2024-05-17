/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
}

group = "ru.pixnews.library.test"

dependencies {
    api(libs.junit.jupiter.api)
    api(libs.kermit.jvm)
    api(libs.kotlinx.coroutines.test)
    api(libs.okhttp.logging.interceptor)
    api(platform(libs.junit.bom))
    api(platform(libs.kotlinx.coroutines.bom))
    api(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.mockwebserver)
}

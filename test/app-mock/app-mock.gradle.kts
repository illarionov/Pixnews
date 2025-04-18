/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
}

android {
    namespace = "ru.pixnews.test.app.mock"
}

dependencies {
    api(projects.foundation.network.public)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
    api(libs.okhttp.mockwebserver)
    api(libs.mockwebserver.dsl)
    implementation(libs.androidx.compose.runtime)

    testImplementation(projects.library.test)
    testImplementation(libs.junit.jupiter.params)
}

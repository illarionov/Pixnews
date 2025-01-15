/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
}

android {
    namespace = "ru.pixnews.library.ui.tooling"
}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.kermit)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.pixnews.debuglayout)

    testImplementation(libs.junit.jupiter.params)
}

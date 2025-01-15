/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
}

android {
    namespace = "ru.pixnews.library.compose.utils"
}

dependencies {
    api(libs.androidx.core)
    api(libs.androidx.compose.foundation)

    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.material3)
}

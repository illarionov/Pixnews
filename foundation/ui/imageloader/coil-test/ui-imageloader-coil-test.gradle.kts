/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
}

android {
    namespace = "ru.pixnews.foundation.ui.imageloader.coil.test"
}

dependencies {
    api(projects.foundation.ui.imageloader.coil)
    api(libs.coil.base)
    api(libs.coil.compose.base)
    implementation(libs.coil.test)
    implementation(libs.junit4)
}

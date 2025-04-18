/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.pixnews.feature.calendar.test.constants"
}

dependencies {
    api(libs.androidx.compose.ui)
    api(projects.feature.calendar.dataPublic)
    api(projects.foundation.ui.design)
    api(libs.kotlinx.datetime)
    implementation(projects.library.kotlinDatetimeUtils)
}

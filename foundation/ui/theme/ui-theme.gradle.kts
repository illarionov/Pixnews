/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    id("ru.pixnews.gradle.project.di.anvil-factories")
}

android {
    namespace = "ru.pixnews.foundation.ui.theme"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.di.base)
    implementation(projects.library.androidUtils)

    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.splashscreen)

    implementation(libs.kotlinx.coroutines.android)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.rules)
}

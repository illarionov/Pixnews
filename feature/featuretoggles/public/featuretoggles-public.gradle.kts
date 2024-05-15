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
    namespace = "ru.pixnews.feature.featuretoggles"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.rootComponent)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.featuretoggles.public)
    implementation(projects.foundation.featuretoggles.datasourceOverrides)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.theme)
    implementation(projects.library.androidUtils)
    implementation(projects.library.composeUtils)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kermit)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(projects.library.uiTooling)
    debugImplementation(libs.androidx.compose.ui.tooling)

    api(libs.inject)

    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(projects.foundation.instrumentedTest)
}

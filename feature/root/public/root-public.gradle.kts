/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.android.test.compose.screenshot")
    id("ru.pixnews.gradle.di.anvil-factories")
}

pixnews {
    compose.set(true)
}

android {
    namespace = "ru.pixnews.feature.root"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    implementation(projects.feature.root.testConstants)

    // TODO should be in app
    implementation(projects.feature.calendar.public)
    implementation(projects.feature.collections.public)
    implementation(projects.feature.profile.public)

    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.theme)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)

    implementation(projects.library.uiTooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    id("ru.pixnews.gradle.project.android.test.compose.screenshot")
    id("ru.pixnews.gradle.project.di.anvil-factories")
}

android {
    namespace = "ru.pixnews.foundation.ui.design"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.ui.theme)
    api(projects.foundation.domainModel)
    implementation(projects.foundation.ui.designTestConstants)
    implementation(projects.foundation.ui.imageloader.coil)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.library.androidUtils)
    implementation(projects.library.composeUtils)
    implementation(projects.library.uiTooling)
    implementation(testFixtures(projects.foundation.domainModel)) {
        because("For use with Composable Preview")
    }

    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)
    api(libs.kotlinx.collections.immutable)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    screenshotTestImplementation(projects.foundation.ui.imageloader.coilTest)
}

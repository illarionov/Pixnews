/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.android.test.compose.screenshot")
    id("ru.pixnews.gradle.project.di.anvil-ksp")
    id("ru.pixnews.gradle.project.kotlin.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.pixnews.feature.calendar"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    implementation(projects.feature.calendar.testConstants)
    implementation(projects.feature.calendar.data)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.theme)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.imageloader.coil)
    implementation(projects.library.composeUtils)
    implementation(projects.library.functional)
    implementation(projects.library.kotlinUtils)

    implementation(libs.accompanist.pager)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)

    implementation(testFixtures(projects.foundation.domainModel)) {
        because("For use with Composable Preview")
    }
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(projects.library.uiTooling)
    implementation(projects.library.kotlinDatetimeUtils)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotest.extensions.arrow)
    testImplementation(libs.turbine)
    testImplementation(projects.library.test)
}

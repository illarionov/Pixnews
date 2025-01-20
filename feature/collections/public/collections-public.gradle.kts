/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    // id("ru.pixnews.gradle.project.di.anvil-ksp")
    id("ru.pixnews.gradle.project.di.anvil-ksp")
}

android {
    namespace = "ru.pixnews.feature.collections"
}

dependencies {
    implementation(projects.feature.collections.testConstants)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.theme)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(projects.library.uiTooling)
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(projects.foundation.instrumentedTest)
}

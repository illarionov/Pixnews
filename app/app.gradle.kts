/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.android.application")
    id("ru.pixnews.gradle.android.crashlytics")
    id("ru.pixnews.gradle.android.test-instrumented")
    id("ru.pixnews.gradle.di.anvil-kapt")
    alias(libs.plugins.fbase.options.gradle.plugin)
    // id("ru.pixnews.gradle.config.firebase")
    id("ru.pixnews.gradle.config.igdb")
}

pixnews {
    compose.set(true)
}

android {
    defaultConfig {
        applicationId = "ru.pixnews"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
        resValues = true
    }

    lint {
        checkDependencies = true
    }
}

dependencies {
    implementation(projects.feature.root.public)

    implementation(projects.feature.calendar.public)
    implementation(projects.feature.calendar.data)
    implementation(projects.feature.calendar.datasourceIgdb)
    implementation(projects.feature.collections.public)
    debugImplementation(projects.feature.featuretoggles.public)
    implementation(projects.feature.profile.public)

    implementation(projects.foundation.analytics)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.di.rootComponent)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.di.workmanager)
    implementation(projects.foundation.coroutines)
    implementation(projects.foundation.database)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.featuretoggles.datasourceFirebase)
    implementation(projects.foundation.featuretoggles.datasourceOverrides)
    implementation(projects.foundation.featuretoggles.internal)
    implementation(projects.foundation.featuretoggles.public)
    implementation(projects.foundation.initializers)
    implementation(projects.foundation.network.public)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.imageloader.coil)
    implementation(projects.foundation.ui.theme)
    implementation(projects.library.androidUtils)
    implementation(projects.library.coroutines)
    implementation(projects.library.functional)
    implementation(projects.library.kotlinUtils)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing)

    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)

    implementation(libs.okio)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    runtimeOnly(libs.kotlinx.serialization.core)

    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter.params)

    androidTestImplementation(projects.test.appMock)
    androidTestImplementation(projects.foundation.instrumentedTest)
    androidTestImplementation(projects.foundation.ui.imageloader.coilTest)
    androidTestImplementation(projects.foundation.ui.designTestConstants)
    androidTestImplementation(projects.feature.calendar.test)
    androidTestImplementation(projects.feature.calendar.testConstants)
    androidTestImplementation(projects.library.composeUtils)
    androidTestImplementation(projects.library.instrumentedTest)
    androidTestImplementation(testFixtures(projects.foundation.domainModel))
    androidTestImplementation(libs.accompanist.test.harness)
    androidTestImplementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.paging.testing)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.assertk)
    androidTestImplementation(libs.turbine)
}

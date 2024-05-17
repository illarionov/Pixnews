/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.di.anvil-factories")
}

pixnews {
    compose.set(true)
}

android {
    namespace = "ru.pixnews.foundation.di.ui.base"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.di.rootComponent)
    api(libs.dagger)
    implementation(projects.library.androidUtils)

    api(libs.androidx.appcompat)
    api(libs.androidx.fragment)
    api(libs.androidx.activity)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.pixnews.anvil.activity.inject)
    api(libs.pixnews.anvil.viewmodel.inject)

    androidTestImplementation(libs.androidx.test.rules)
}

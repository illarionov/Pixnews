/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.di.anvil-factories")
}

pixnews {
    compose.set(true)
}

android {
    namespace = "ru.pixnews.foundation.ui.imageloader.coil"
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.di.rootComponent)
    api(projects.foundation.domainModel)
    implementation(projects.foundation.network.public)
    implementation(projects.library.androidUtils)

    api(libs.kermit)
    api(libs.dagger)
    api(libs.coil.base)
    api(libs.coil.compose.base)
    api(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.gif)
    implementation(libs.coil.svg)
    implementation(libs.coil.video)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
}

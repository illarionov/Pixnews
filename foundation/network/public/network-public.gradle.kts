/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.di.anvil-ksp")
}

android {
    namespace = "ru.pixnews.foundation.network"
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.di.rootComponent)
    implementation(projects.library.androidUtils)

    api(libs.inject)
    api(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    api(platform(libs.okhttp.bom))
    api(libs.androidx.annotation)
}

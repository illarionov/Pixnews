/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.foundation.di.root.component"
}

dependencies {
    api(projects.foundation.di.base)
    api(projects.foundation.analytics)
    api(projects.foundation.appconfig)
    api(projects.foundation.coroutines)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.featuretoggles.datasourceOverrides)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.kermit)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.di.anvil-factories")
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.foundation.di.workmanager"

    lint {
        disable += "BadConfigurationProvider"
    }
}

dependencies {
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.di.rootComponent)
    api(libs.dagger)

    api(libs.pixnews.anvil.workmanager.inject)
    api(libs.androidx.workmanager.ktx)
    api(libs.androidx.core)
}

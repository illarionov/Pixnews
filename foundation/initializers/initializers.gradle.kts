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
    namespace = "ru.pixnews.foundation.initializers"
}

dependencies {
    implementation(projects.library.coroutines)
    api(libs.pixnews.anvil.initializer.inject)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.tracing)
}

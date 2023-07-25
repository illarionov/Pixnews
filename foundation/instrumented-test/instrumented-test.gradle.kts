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
    namespace = "ru.pixnews.foundation.testing.instrumented"

    lint {
        disable += "RemoveWorkManagerInitializer"
    }
}

dependencies {
    api(libs.androidx.annotation)
    api(libs.androidx.test.core)
    api(libs.androidx.test.ext.junit)
    api(libs.androidx.test.runner)
    api(libs.assertk)
    api(libs.dagger)
    api(libs.junit4)

    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.espresso.core)
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.di.rootComponent)
    implementation(projects.foundation.di.workmanager)
    implementation(projects.library.instrumentedTest)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.di.anvil-factories")
    id("ru.pixnews.gradle.project.protobuf-wire")
}

pixnews {
    compose.set(false)
    unitTestEngine.set(ru.pixnews.gradle.project.base.UnitTestEngine.JUNIT5)
}

android {
    namespace = "ru.pixnews.foundation.featuretoggles.datasource.overrides"
}

dependencies {
    api(projects.foundation.coroutines)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.featuretoggles.internal)
    api(projects.library.coroutines)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore)

    testImplementation(projects.library.test)
    testImplementation(testFixtures(projects.foundation.featuretoggles.public))
    testImplementation(libs.turbine)
}

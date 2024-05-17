/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
}

group = "ru.pixnews.foundation.featuretoggles.internal"

dependencies {
    api(projects.foundation.featuretoggles.public)
    implementation(projects.library.functional)
    implementation(platform(libs.kotlinx.coroutines.bom))
    api(libs.kermit)
    api(libs.kotlinx.coroutines.core)

    testImplementation(projects.library.test)
    testImplementation(testFixtures(projects.foundation.featuretoggles.public))
    testImplementation(libs.turbine)
}

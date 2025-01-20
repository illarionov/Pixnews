/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    `java-test-fixtures`
}

group = "ru.pixnews.foundation.featuretoggles"

dependencies {
    api(libs.dagger)
    api(projects.library.functional)
    api(libs.pixnews.anvil.experiment.inject)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
}

pixnews {
    compose.set(false)
}

group = "ru.pixnews.feature.calendar.data"

dependencies {
    api(projects.library.coroutines)
    api(libs.androidx.paging.common)
    implementation(projects.foundation.domainModel)
}

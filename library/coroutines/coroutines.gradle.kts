/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
}

group = "ru.pixnews.library.coroutines"

dependencies {
    api(projects.library.functional)
    api(libs.kotlinx.coroutines.core)
    api(platform(libs.kotlinx.coroutines.bom))
}

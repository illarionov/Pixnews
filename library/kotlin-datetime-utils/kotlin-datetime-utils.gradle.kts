/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.kotlin.library")
    `java-test-fixtures`
}

group = "ru.pixnews.library.kotlin.datetime.utils"

dependencies {
    implementation(libs.kotlinx.datetime)
}

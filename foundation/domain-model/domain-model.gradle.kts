/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
    id("ru.pixnews.gradle.project.kotlin.compose")
    `java-test-fixtures`
}

group = "ru.pixnews.foundation.domain.model"

dependencies {
    api(libs.kotlinx.collections.immutable)
    api(projects.library.functional)
    api(libs.kotlinx.datetime)
    api(libs.androidx.annotation)
    api(projects.library.internationalization)
    api(projects.library.kotlinUtils) // temporary set to api for compose preview to work
    implementation(projects.library.kotlinDatetimeUtils)

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotest.property)
}

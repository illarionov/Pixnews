/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
    id("ru.pixnews.gradle.project.di.anvil-factories")
    id("ru.pixnews.gradle.project.testing.graalvm")
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.feature.calendar.data"
}

dependencies {
    api(projects.feature.calendar.dataPublic)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.database)
    implementation(projects.foundation.coroutines)
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.network.public)
    implementation(projects.library.functional)
    implementation(projects.library.kotlinUtils)
    implementation(projects.library.coroutines)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(testFixtures(projects.foundation.domainModel))
    testImplementation(projects.library.test)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.pixnews.sqlite.open.helper.main)
    testImplementation(libs.pixnews.sqlite.open.helper.graal)
    testImplementation(libs.pixnews.sqlite.open.helper.chicory)
    testImplementation(libs.pixnews.sqlite.open.helper.chasm)

    testImplementation(libs.pixnews.sqlite.open.helper.sqlite.mt)
    testImplementation(libs.pixnews.sqlite.open.helper.sqlite.st)
}

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
    namespace = "ru.pixnews.feature.calendar.datasource.igdb"
}

dependencies {
    api(projects.feature.calendar.dataPublic)
    implementation(projects.feature.calendar.data)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.network.public)
    implementation(projects.library.functional)
    implementation(projects.library.kotlinDatetimeUtils)
    implementation(projects.library.kotlinUtils)

    api(libs.inject)
    api(libs.igdbclient)
    implementation(libs.igdbclient.okhttp)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.okhttp)

    testImplementation(projects.library.test)
    testImplementation(libs.junit.jupiter.params)
}

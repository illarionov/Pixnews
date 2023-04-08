/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.android.test-paparazzi")
    id("ru.pixnews.gradle.di.anvil-factories")
    id("kotlin-parcelize")
}

pixnews {
    compose.set(true)
    managedDevices.set(true)
}

android {
    namespace = "ru.pixnews.feature.calendar"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    implementation(projects.feature.calendar.testConstants)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.theme)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.imageloader.coil)
    implementation(projects.library.composeUtils)
    implementation(projects.library.kotlinUtils)

    implementation(libs.accompanist.pager)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)

    implementation(testFixtures(projects.foundation.domainModel)) {
        because("For use with Composable Preview")
    }
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(projects.library.uiTooling)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit.jupiter.params)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(projects.foundation.instrumentedTest)
    androidTestImplementation(projects.feature.calendar.test)
}

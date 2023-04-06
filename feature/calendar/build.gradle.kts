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
    id("ru.pixnews.android-library")
    id("ru.pixnews.di-anvil-factories")
    id("ru.pixnews.android-testing-paparazzi")
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
    implementation(project(":foundation:appconfig"))
    implementation(project(":foundation:di:ui-base"))
    implementation(project(":foundation:domain-model"))
    implementation(project(":foundation:ui:assets-icons"))
    implementation(project(":foundation:ui:theme"))
    implementation(project(":foundation:ui:design"))
    implementation(project(":foundation:ui:imageloader:coil"))
    implementation(project(":library:compose-utils"))
    implementation(project(":library:kotlin-utils"))

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

    implementation(testFixtures(project(":foundation:domain-model"))) {
        because("For use with Composable Preview")
    }
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(project(":library:ui-tooling"))
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit.jupiter.params)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(project(":foundation:instrumented-testing"))
    androidTestImplementation(project(":feature:calendar-testing"))
}

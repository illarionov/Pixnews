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
}

pixnews {
    compose.set(true)
    managedDevices.set(true)
}

android {
    namespace = "ru.pixnews.feature.root"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    // TODO should be in app
    implementation(project(":feature:calendar"))
    implementation(project(":feature:collections"))
    implementation(project(":feature:profile"))

    implementation(project(":foundation:appconfig"))
    implementation(project(":foundation:di:ui-base"))
    implementation(project(":foundation:domain-model"))
    implementation(project(":foundation:ui:assets-icons"))
    implementation(project(":foundation:ui:design"))
    implementation(project(":foundation:ui:theme"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)

    implementation(project(":library:ui-tooling"))
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(project(":foundation:instrumented-test"))
    androidTestImplementation(libs.accompanist.test.harness)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.assertk)
    androidTestImplementation(libs.turbine)
}

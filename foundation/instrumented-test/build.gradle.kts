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
    id("ru.pixnews.di-anvil-kapt")
}

pixnews {
    compose.set(true)
    managedDevices.set(false)
}

android {
    namespace = "ru.pixnews.foundation.testing.instrumented"
}

dependencies {
    api(project(":foundation:di:base"))
    api(project(":foundation:appconfig"))

    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.kermit)
    implementation(libs.radiography)

    implementation(libs.androidx.compose.ui.test.junit4)
    api(libs.androidx.test.core)
    implementation(libs.androidx.test.espresso.core)
    api(libs.androidx.test.ext.junit)
    api(libs.assertk)
}
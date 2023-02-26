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
}

pixnews {
    compose.set(false)
    managedDevices.set(false)
}

android {
    namespace = "ru.pixnews.foundation.di.ui.base"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(project(":foundation:appconfig"))
    api(project(":foundation:di:base"))
    api(project(":foundation:di:root-component"))
    api(libs.dagger)
    implementation(project(":libraries:android-utils"))

    api(libs.androidx.appcompat)
    api(libs.androidx.fragment)
    api(libs.androidx.activity)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.lifecycle.viewmodel.compose)

    androidTestImplementation(libs.androidx.test.rules)
}

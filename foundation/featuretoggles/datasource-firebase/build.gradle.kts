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
    // https://github.com/square/anvil/issues/668
    id("ru.pixnews.di-anvil-kapt")
}

pixnews {
    compose.set(false)
    managedDevices.set(false)
}

android {
    namespace = "ru.pixnews.foundation.featuretoggles.datasource.firebase"
}

dependencies {
    api(project(":foundation:appconfig"))
    api(project(":foundation:di"))
    api(project(":foundation:dispatchers"))
    api(project(":foundation:featuretoggles:pub"))
    api(project(":foundation:featuretoggles:internal"))

    api(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    api(libs.firebase.config)
    implementation(libs.kotlinx.coroutines.core)
}

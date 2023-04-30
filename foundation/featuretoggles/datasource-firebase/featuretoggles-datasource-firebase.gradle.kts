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
    id("ru.pixnews.gradle.di.anvil-factories")
}

pixnews {
    compose.set(false)
    managedDevices.set(false)
}

android {
    namespace = "ru.pixnews.foundation.featuretoggles.datasource.firebase"
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.coroutines)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.featuretoggles.internal)
    implementation(projects.library.coroutines)

    implementation(libs.firebase.analytics)
    api(libs.firebase.config)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(projects.library.test)
    testImplementation(testFixtures(projects.foundation.featuretoggles.public))
    testImplementation(libs.mockk)
}

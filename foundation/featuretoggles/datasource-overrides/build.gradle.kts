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
    id("ru.pixnews.protobuf-lite-java")
    id("ru.pixnews.di-anvil-factories")
}

pixnews {
    compose.set(false)
    managedDevices.set(false)
    unitTestEngine.set(ru.pixnews.UnitTestEngine.JUNIT5)
}

android {
    namespace = "ru.pixnews.foundation.featuretoggles.datasource.overrides"
}

dependencies {
    api(project(":foundation:dispatchers"))
    api(project(":foundation:featuretoggles:public"))
    api(project(":foundation:featuretoggles:internal"))
    api(project(":libraries:coroutines"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore)

    testImplementation(project(":libraries:testing"))
    testImplementation(testFixtures(project(":foundation:featuretoggles:public")))
    testImplementation(libs.turbine)
}

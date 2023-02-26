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
    compose.set(true)
    managedDevices.set(true)
}

android {
    namespace = "ru.pixnews.foundation.ui.design"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(project(":foundation:appconfig"))
    api(project(":foundation:di:base"))
    api(project(":foundation:featuretoggles:public"))
    api(project(":foundation:ui:theme"))
    api(project(":foundation:domain-model"))
    implementation(project(":foundation:ui:assets-icons"))
    implementation(project(":libraries:android-utils"))
    implementation(project(":libraries:ui-tooling"))
    implementation(testFixtures(project(":foundation:domain-model"))) {
        because("For use with Composable Preview")
    }

    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)
    api(libs.kotlinx.collections.immutable)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    androidTestImplementation(libs.androidx.test.rules)
}

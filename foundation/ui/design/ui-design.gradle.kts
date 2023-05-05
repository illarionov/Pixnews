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
    managedDevices.set(false)
}

android {
    namespace = "ru.pixnews.foundation.ui.design"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(projects.foundation.appconfig)
    api(projects.foundation.di.base)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.ui.theme)
    api(projects.foundation.domainModel)
    implementation(projects.foundation.ui.designTestConstants)
    implementation(projects.foundation.ui.imageloader.coil)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.library.androidUtils)
    implementation(projects.library.composeUtils)
    implementation(projects.library.uiTooling)
    implementation(testFixtures(projects.foundation.domainModel)) {
        because("For use with Composable Preview")
    }

    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)
    api(libs.accompanist.placeholder.material3)
    api(libs.kotlinx.collections.immutable)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(projects.foundation.ui.imageloader.coilTest)
}

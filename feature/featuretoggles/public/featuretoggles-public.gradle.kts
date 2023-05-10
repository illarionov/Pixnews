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
    compose.set(true)
}

android {
    namespace = "ru.pixnews.feature.featuretoggles"
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.di.rootComponent)
    implementation(projects.foundation.di.uiBase)
    implementation(projects.foundation.featuretoggles.public)
    implementation(projects.foundation.featuretoggles.datasourceOverrides)
    implementation(projects.foundation.ui.assetsIcons)
    implementation(projects.foundation.ui.design)
    implementation(projects.foundation.ui.theme)
    implementation(projects.library.androidUtils)
    implementation(projects.library.composeUtils)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kermit)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(projects.library.uiTooling)
    debugImplementation(libs.androidx.compose.ui.tooling)

    api(libs.inject)

    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(projects.foundation.instrumentedTest)
}

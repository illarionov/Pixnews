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
    id("ru.pixnews.android-application")
    id("ru.pixnews.di-anvil-kapt")
}

pixnews {
    compose.set(true)
    managedDevices.set(true)
}

android {
    defaultConfig {
        applicationId = "ru.pixnews"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    lint {
        checkDependencies = true
    }
}

dependencies {
    implementation(project(":foundation:appconfig"))
    implementation(project(":foundation:analytics"))
    implementation(project(":foundation:di"))
    implementation(project(":foundation:ui-theme"))
    implementation(project(":foundation:dispatchers"))
    implementation(project(":foundation:initializers"))
    implementation(project(":libraries:coroutines"))
    implementation(project(":libraries:functional"))
    implementation(project(":libraries:kotlin-utils"))

    implementation(libs.kermit)

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.startup)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)

    testImplementation(libs.junit4)
}

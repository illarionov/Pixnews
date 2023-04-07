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
    id("ru.pixnews.gradle.android.application")
    id("ru.pixnews.gradle.android.crashlytics")
    id("ru.pixnews.gradle.di.anvil-kapt")
    alias(libs.plugins.google.services)
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
        buildConfig = true
    }

    lint {
        checkDependencies = true
    }

    sourceSets {
        listOf("release", "benchmark").forEach { sourceSet ->
            getByName(sourceSet) {
                kotlin.srcDir("src/sharedReleaseBenchmark/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":feature:root"))

    implementation(project(":feature:calendar"))
    implementation(project(":feature:collections"))
    debugImplementation(project(":feature:featuretoggles"))
    implementation(project(":feature:profile"))

    implementation(project(":foundation:analytics"))
    implementation(project(":foundation:appconfig"))
    implementation(project(":foundation:di:base"))
    implementation(project(":foundation:di:root-component"))
    implementation(project(":foundation:di:ui-base"))
    implementation(project(":foundation:dispatchers"))
    implementation(project(":foundation:domain-model"))
    implementation(project(":foundation:featuretoggles:datasource-firebase"))
    implementation(project(":foundation:featuretoggles:datasource-overrides"))
    implementation(project(":foundation:featuretoggles:internal"))
    implementation(project(":foundation:featuretoggles:public"))
    implementation(project(":foundation:initializers"))
    implementation(project(":foundation:network:public"))
    implementation(project(":foundation:ui:design"))
    implementation(project(":foundation:ui:imageloader:coil"))
    implementation(project(":foundation:ui:theme"))
    implementation(project(":library:android-utils"))
    implementation(project(":library:coroutines"))
    implementation(project(":library:functional"))
    implementation(project(":library:kotlin-utils"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing)

    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)

    implementation(libs.okio)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    runtimeOnly(libs.kotlinx.serialization.core)

    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter.params)

    androidTestImplementation(project(":foundation:instrumented-test"))
    androidTestImplementation(project(":foundation:di:instrumented-test"))
    androidTestImplementation(project(":feature:calendar-test"))
    androidTestImplementation(testFixtures(project(":foundation:domain-model")))
    androidTestImplementation(libs.accompanist.test.harness)
    androidTestImplementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.assertk)
}

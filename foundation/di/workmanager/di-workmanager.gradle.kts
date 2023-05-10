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
}

android {
    namespace = "ru.pixnews.foundation.di.workmanager"

    lint {
        disable += "BadConfigurationProvider"
    }
}

dependencies {
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.di.rootComponent)
    api(libs.dagger)

    api(libs.androidx.workmanager.ktx)
    api(libs.androidx.core)
}

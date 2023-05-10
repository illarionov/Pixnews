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
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.foundation.di.root.component"
}

dependencies {
    api(projects.foundation.di.base)
    api(projects.foundation.analytics)
    api(projects.foundation.appconfig)
    api(projects.foundation.coroutines)
    api(projects.foundation.featuretoggles.public)
    api(projects.foundation.featuretoggles.datasourceOverrides)
    api(libs.kermit)
}

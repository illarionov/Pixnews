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
    id("ru.pixnews.gradle.kotlin.library")
}

group = "ru.pixnews.library.test"

dependencies {
    api(platform(libs.junit.bom))
    api(platform(libs.kotest.bom))
    api(libs.junit.jupiter.api)

    api(libs.kermit.jvm)

    api(libs.kotlinx.coroutines.test)
}

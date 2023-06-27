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
    id("ru.pixnews.gradle.protobuf-wire")
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.library.igdb"
}

wire {
    kotlin {
        emitDeclaredOptions = false
        emitAppliedOptions = false
    }
}

dependencies {
    api(libs.okhttp)
    implementation(libs.kermit)
    implementation(projects.library.coroutines)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.okhttp.logging.interceptor)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.org.json)
    testImplementation(projects.library.test)
}

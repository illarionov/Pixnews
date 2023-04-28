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

import com.android.build.gradle.LibraryPlugin
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin with paparazzi configuration
 */
plugins {
    id("com.android.library") apply false
    id("app.cash.paparazzi")
}

plugins.withType<LibraryPlugin>() {
    android {
        buildFeatures {
            // https://github.com/cashapp/paparazzi/issues/472
            androidResources = true
        }
        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }

    dependencies {
        "testImplementation"(versionCatalog.findLibrary("junit-jupiter-vintage-engine").get())
    }
}

tasks.withType<Test> {
    // https://github.com/cashapp/paparazzi/pull/739
    systemProperty("kotlinx.coroutines.main.delay", true)
}

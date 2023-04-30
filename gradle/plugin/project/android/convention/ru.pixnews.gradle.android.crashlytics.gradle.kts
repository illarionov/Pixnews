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

import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures crashlytics
 */
plugins {
    id("com.android.application") apply false
    id("ru.pixnews.gradle.base.build-parameters")
    id("com.google.firebase.crashlytics")
}

extensions.configure<ApplicationExtension>("android") {
    buildTypes {
        all {
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
        release {
            manifestPlaceholders["firebase_crashlytics_collection_enabled"] = "true"
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = buildParameters.releasing
            }
        }
    }
}

dependencies {
    implementation(versionCatalog.findLibrary("firebase.analytics").get())
    implementation(versionCatalog.findLibrary("firebase.crashlytics").get())
}

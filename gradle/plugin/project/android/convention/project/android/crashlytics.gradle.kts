/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures crashlytics
 */
plugins {
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
    add("implementation", versionCatalog.findLibrary("firebase.analytics").get())
    add("implementation", versionCatalog.findLibrary("firebase.crashlytics").get())
}

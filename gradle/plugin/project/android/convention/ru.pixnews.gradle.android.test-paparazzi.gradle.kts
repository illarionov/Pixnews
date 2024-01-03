/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin with paparazzi configuration
 */
plugins {
    id("com.android.library") apply false
    id("app.cash.paparazzi")
}

plugins.withType<LibraryPlugin> {
    extensions.configure<LibraryExtension>("android") {
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

tasks.withType<Test>().configureEach {
    forkEvery = 1
}

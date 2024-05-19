/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("UnstableApiUsage")

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.api.AndroidBasePlugin
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures Android Compose Screenshot Testing
 */
plugins {
    id("com.android.compose.screenshot")
}

plugins.withType(AndroidBasePlugin::class.java) {
    extensions.configure(CommonExtension::class.java) {
        experimentalProperties["android.experimental.enableScreenshotTest"] = true
    }

    dependencies {
        add("screenshotTestImplementation", versionCatalog.findLibrary("androidx-compose-ui-tooling").get())
    }
}

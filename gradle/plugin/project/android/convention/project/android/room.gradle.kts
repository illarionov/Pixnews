/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.android

import androidx.room.gradle.RoomExtension
import com.android.build.api.dsl.CommonExtension
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures Room
 */
plugins {
    id("androidx.room")
    id("com.google.devtools.ksp")
    id("ru.pixnews.gradle.base.build-parameters")
}

extensions.configure<RoomExtension> {
    schemaDirectory("$projectDir/schemas/")
}

extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.generateKotlin" to "true",
                    "room.incremental" to "true",
                )
            }
        }
    }
}

dependencies {
    add("annotationProcessor", versionCatalog.findLibrary("androidx.room.compiler").get())
    add("api", versionCatalog.findLibrary("androidx.room").get())
    add("ksp", versionCatalog.findLibrary("androidx.room.compiler").get())

    add("testImplementation", versionCatalog.findLibrary("androidx.room.testing").get())
}

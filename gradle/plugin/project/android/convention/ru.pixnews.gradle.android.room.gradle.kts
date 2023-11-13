/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.CommonExtension
import org.gradle.kotlin.dsl.apply
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures Room
 */
plugins {
    id("com.android.library") apply false
    id("androidx.room")
    id("com.google.devtools.ksp")
    id("ru.pixnews.gradle.base.build-parameters")
}

room {
    schemaDirectory("$projectDir/schemas/")
}

extensions.configure<CommonExtension<*, *, *, *, *>>("android") {
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
    annotationProcessor(versionCatalog.findLibrary("androidx.room.compiler").get())
    api(versionCatalog.findLibrary("androidx.room").get())
    ksp(versionCatalog.findLibrary("androidx.room.compiler").get())

    testImplementation(versionCatalog.findLibrary("androidx.room.testing").get())
}

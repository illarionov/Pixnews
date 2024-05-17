/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.library")
}

pixnews {
    compose.set(true)
}

android {
    namespace = "ru.pixnews.foundation.ui.assets.icons"

    lint {
        // Module with mostly automatically generated source code.
        // Enable only simple checks that do not touch Kotlin files.
        // Adding any check targeting Kotlin source code greatly increases execution time of the `lintAnalyzeRelease`
        // task.
        checkOnly += listOf(
            "AnnotationProcessorOnCompilePath",
        )
    }
}

dependencies {
    api(projects.foundation.ui.theme)
    implementation(projects.library.uiTooling)
    implementation(projects.library.androidUtils)

    api(libs.androidx.compose.ui.graphics)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}

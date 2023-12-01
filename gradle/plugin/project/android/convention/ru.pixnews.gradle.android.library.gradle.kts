/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import ru.pixnews.gradle.android.applyTo
import ru.pixnews.gradle.android.configureCommonAndroid
import ru.pixnews.gradle.base.createPixnewsExtension
import ru.pixnews.gradle.base.pixnews

/**
 * Convention plugin for use in android library modules
 */
plugins {
    id("com.android.library")
    kotlin("android")
    id("ru.pixnews.gradle.base.build-parameters")
    id("ru.pixnews.gradle.lint.android-lint")
}

createPixnewsExtension()

extensions.configure<LibraryExtension>("android") {
    configureCommonAndroid(this)

    defaultConfig {
        consumerProguardFiles("lib-proguard-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            matchingFallbacks += "release"
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>()
        .matching { task ->
            !(task is KaptGenerateStubsTask || task.name.endsWith("TestKotlin"))
        }
        .configureEach {
            compilerOptions {
                freeCompilerArgs.addAll("-Xexplicit-api=warning")
            }
        }
}

androidComponents {
    finalizeDsl {
        project.pixnews.applyTo(project, it)
    }
    beforeVariants(selector().withBuildType("debug")) { builder ->
        // Debug variant is usually required for Composable functions preview in modules with Compose
        if (!project.pixnews.compose.get()) {
            builder.enable = false
        }
    }
}

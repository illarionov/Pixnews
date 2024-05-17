/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import ru.pixnews.gradle.project.base.createPixnewsExtension
import ru.pixnews.gradle.project.base.pixnews
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures android application
 */
plugins {
    id("com.android.application")
    kotlin("android")
    id("ru.pixnews.gradle.base.build-parameters")
    id("ru.pixnews.gradle.project.lint.android-lint")
}

createPixnewsExtension().apply {
    compose.convention(true)
}

extensions.configure<ApplicationExtension>("android") {
    configureCommonAndroid(this)

    defaultConfig {
        targetSdk = versionCatalog.findVersion("targetSdk").get().displayName.toInt()
        manifestPlaceholders += listOf(
            "firebase_crashlytics_collection_enabled" to "false",
            "firebase_analytics_collection_deactivated" to "true",
            "google_analytics_adid_collection_enabled" to "false",
        )
    }

    val releaseConfig = ReleaseConfig(project)
    configureSigning(signingConfigs, releaseConfig)

    buildTypes {
        val release = getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(if (releaseConfig.useReleaseKeystore) "release" else "debug")
            manifestPlaceholders += listOf(
                "firebase_crashlytics_collection_enabled" to "true",
                "firebase_analytics_collection_deactivated" to "false",
                "google_analytics_adid_collection_enabled" to "true",
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("instrumentedTests", "release")
            manifestPlaceholders += listOf(
                "firebase_crashlytics_collection_enabled" to "false",
                "firebase_analytics_collection_deactivated" to "true",
                "google_analytics_adid_collection_enabled" to "false",
            )
        }
        create("benchmark") {
            initWith(release)
            applicationIdSuffix = ".benchmark"
            signingConfig = signingConfigs.getByName("debug")

            // https://issuetracker.google.com/issues/266687543
            isShrinkResources = false
            isMinifyEnabled = false

            proguardFiles("proguard-benchmark.pro")
            matchingFallbacks += "release"

            manifestPlaceholders += listOf(
                "firebase_crashlytics_collection_enabled" to "false",
                "firebase_analytics_collection_deactivated" to "true",
                "google_analytics_adid_collection_enabled" to "false",
            )
        }
    }

    packaging.resources.excludes += listOf(
        "**/*.properties",
        "**/*.proto",
        "*.properties",
        "LICENSE.txt",
        "LICENSE_OFL",
        "LICENSE_UNICODE",
        "META-INF/CHANGES",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/NOTICE",
        "META-INF/NOTICE.txt",
    )
}

extensions.configure<ApplicationAndroidComponentsExtension> {
    onVariants(selector().withName("release")) { variant ->
        variant.packaging.resources.excludes.addAll(
            "DebugProbesKt.bin",
            "META-INF/*.kotlin_module",
            "META-INF/*.version",
            "META-INF/androidx.*",
            "kotlin/**",
        )
    }
    finalizeDsl {
        project.pixnews.applyTo(project, it)
    }
}

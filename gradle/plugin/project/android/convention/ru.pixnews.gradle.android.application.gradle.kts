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

import ru.pixnews.gradle.android.ReleaseConfig
import ru.pixnews.gradle.android.applyTo
import ru.pixnews.gradle.android.configureCommonAndroid
import ru.pixnews.gradle.android.configureSigning
import ru.pixnews.gradle.base.createPixnewsExtension
import ru.pixnews.gradle.base.pixnews
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures android application
 */
plugins {
    id("com.android.application")
    kotlin("android")
    id("ru.pixnews.gradle.base.build-parameters")
    id("ru.pixnews.gradle.lint.android-lint")
}

createPixnewsExtension().apply {
    compose.convention(true)
    managedDevices.convention(true)
}

android {
    configureCommonAndroid(this)

    defaultConfig {
        targetSdk = versionCatalog.findVersion("targetSdk").get().displayName.toInt()
        manifestPlaceholders["firebase_crashlytics_collection_enabled"] = "false"
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
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += "release"
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
        }
    }

    packagingOptions.resources.excludes += listOf(
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

androidComponents {
    onVariants(selector().withName("release")) { variant ->
        variant.packaging.resources.excludes.addAll(
            "DebugProbesKt.bin",
            "META-INF/*.kotlin_module",
            "META-INF/*.version",
            "META-INF/androidx.*",
            "kotlin/**",
        )
    }
    onVariants(selector().withName("androidTest")) { variant ->
        variant.manifestPlaceholders.put("firebase_crashlytics_collection_enabled", "false")
    }
    finalizeDsl {
        project.pixnews.applyTo(project, it)
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
        implementation(platform(it))
        testImplementation(platform(it))
        androidTestImplementation(platform(it))
    }
}

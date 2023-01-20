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

import ru.pixnews.applyTo
import ru.pixnews.configureCommonAndroid
import ru.pixnews.createPixnewsExtension
import ru.pixnews.pixnews
import ru.pixnews.versionCatalog
import java.io.StringReader
import java.util.Properties

/**
 * Convention plugin that configures android application
 */
plugins {
    id("com.android.application")
    kotlin("android")
    id("ru.pixnews.build-parameters")
}

createPixnewsExtension().apply {
    compose.convention(true)
    managedDevices.convention(true)
}

android {
    configureCommonAndroid(this)

    defaultConfig {
        targetSdk = 33
    }

    val releaseKeystorePropertiesFilePath = buildParameters.signing.release_keystore_properties_file
    val releaseKeystorePropertiesContent = providers.fileContents(
        rootProject.layout.projectDirectory.file(releaseKeystorePropertiesFilePath),
    ).asText
    val useReleaseKeystore = releaseKeystorePropertiesContent.isPresent &&
            !buildParameters.signing.sign_with_debug_keys

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("config/signing/debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (useReleaseKeystore) {
            val releaseProperties = Properties().apply {
                load(StringReader(releaseKeystorePropertiesContent.get()))
            }
            create("release") {
                storeFile = rootProject.file(releaseProperties["storeFile"] as String)
                keyAlias = releaseProperties["keyAlias"] as String
                storePassword = releaseProperties["storePassword"] as String
                keyPassword = releaseProperties["keyPassword"] as String
            }
        } else {
            create("release")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(if (useReleaseKeystore) "release" else "debug")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += "release"
        }
    }

    packagingOptions.resources.excludes += listOf(
        "**/*.properties",
        "*.properties",
        "DebugProbesKt.bin",
        "kotlin/**",
        "LICENSE.txt",
        "LICENSE_OFL",
        "LICENSE_UNICODE",
        "META-INF/*.kotlin_module",
        "META-INF/*.version",
        "META-INF/androidx.*",
        "META-INF/CHANGES",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/NOTICE",
        "META-INF/NOTICE.txt",
    )
}

androidComponents {
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

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

/**
 * Convention plugin for use in android library modules
 */
plugins {
    id("com.android.library")
    kotlin("android")
    id("ru.pixnews.build-parameters")
}

createPixnewsExtension()

android {
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
    }

    lint {
        checkDependencies = false
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
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
        if (!project.pixnews.managedDevices.get()) {
            builder.enable = false
        }
    }
}

dependencies {
    versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
        implementation(platform(it))
        testImplementation(platform(it))
        androidTestImplementation(platform(it))
    }

    androidTestImplementation(kotlin("test"))
    testImplementation(kotlin("test"))
}

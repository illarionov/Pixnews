/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.project.android.test")
    id("ru.pixnews.gradle.project.android.test-instrumented")
}

pixnews {
    compose.set(false)
}

@Suppress("VariableNaming")
val APP_BUILD_TYPE_SUFFIX: String = "APP_BUILD_TYPE_SUFFIX"

android {
    namespace = "ru.pixnews.test.benchmark"

    defaultConfig {
        buildConfigField("String", APP_BUILD_TYPE_SUFFIX, "\"\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        create("benchmark") {
            // Keep the build type debuggable so we can attach a debugger if needed.
            isDebuggable = true
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", APP_BUILD_TYPE_SUFFIX, "\".benchmark\"")
        }
    }

    targetProjectPath = ":app"

    // Enable the benchmark to run separately from the app process
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

androidComponents {
    finalizeDsl { extension ->
        extension.defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    beforeVariants {
        it.enable = it.buildType == "benchmark"
    }
}

dependencies {
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.test.ext.junit)
    implementation(projects.feature.calendar.testConstants)
    implementation(projects.feature.root.testConstants)
    implementation(projects.feature.collections.testConstants)
    implementation(projects.feature.profile.testConstants)
}

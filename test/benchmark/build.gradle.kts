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
plugins {
    id("ru.pixnews.android-test")
}

pixnews {
    compose.set(false)
    managedDevices.set(true)
}

@Suppress("VariableNaming")
val APP_BUILD_TYPE_SUFFIX: String = "APP_BUILD_TYPE_SUFFIX"

android {
    namespace = "ru.pixnews.test.benchmark"

    defaultConfig {
        buildConfigField("String", APP_BUILD_TYPE_SUFFIX, "\"\"")
        testInstrumentationRunnerArguments["androidx.benchmark.fullTracing.enable"] = "true"
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
}

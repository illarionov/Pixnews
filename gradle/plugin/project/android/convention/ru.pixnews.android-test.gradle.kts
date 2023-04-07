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
import ru.pixnews.configureSigning
import ru.pixnews.createPixnewsExtension
import ru.pixnews.pixnews
import ru.pixnews.versionCatalog

/**
 * Convention plugin for modules with "blackbox"-style instrumented tests  (UiAutomator, Benchmark)
 */
plugins {
    id("com.android.test")
    kotlin("android")
    id("ru.pixnews.gradle.base.build-parameters")
}

createPixnewsExtension()

android {
    configureCommonAndroid(this)

    defaultConfig {
        targetSdk = versionCatalog.findVersion("targetSdk").get().displayName.toInt()
    }

    configureSigning(signingConfigs)

    lint {
        checkDependencies = false
    }
}

androidComponents {
    finalizeDsl {
        project.pixnews.applyTo(project, it)
    }
}

dependencies {
    listOf(
        "androidx-test-core",
        "androidx-test-rules",
        "androidx-test-espresso-core",
        "androidx-test-runner",
        "androidx-test-rules",
        "androidx-test-uiautomator",
    ).forEach {
        implementation(versionCatalog.findLibrary(it).get())
    }

    implementation(versionCatalog.findLibrary("kotlinx.coroutines.bom").get())
}

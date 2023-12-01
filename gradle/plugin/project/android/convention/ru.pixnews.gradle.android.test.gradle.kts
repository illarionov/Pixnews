/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.TestExtension
import ru.pixnews.gradle.android.applyTo
import ru.pixnews.gradle.android.configureCommonAndroid
import ru.pixnews.gradle.android.configureSigning
import ru.pixnews.gradle.base.createPixnewsExtension
import ru.pixnews.gradle.base.pixnews
import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin for modules with "blackbox"-style instrumented tests  (UiAutomator, Benchmark)
 */
plugins {
    id("com.android.test")
    kotlin("android")
    id("ru.pixnews.gradle.base.build-parameters")
    id("ru.pixnews.gradle.lint.android-lint")
}

createPixnewsExtension()

extensions.configure<TestExtension>("android") {
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
}

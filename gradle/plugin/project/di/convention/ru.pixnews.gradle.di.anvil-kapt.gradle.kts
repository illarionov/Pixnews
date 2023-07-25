/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures anvil with kapt
 */
plugins {
    kotlin("kapt")
    id("com.squareup.anvil")
}

dependencies {
    add("anvil", project(":foundation:di:anvil-codegen"))
    add("api", versionCatalog.findLibrary("dagger").orElseThrow())
    add("kapt", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
}

kapt {
    javacOptions {
        option("-Adagger.fastInit=enabled")
        option("-Adagger.strictMultibindingValidation=enabled")
        option("-Adagger.experimentalDaggerErrorMessages=enabled")
    }
}

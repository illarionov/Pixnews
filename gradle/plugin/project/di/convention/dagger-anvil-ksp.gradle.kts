/*
 * Copyright (c) 2023-2025, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package ru.pixnews.gradle.project.di

import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures Dagger-KSP and Anvil
 */
plugins {
    id("ru.pixnews.gradle.project.di.anvil-ksp")
    id("com.google.devtools.ksp")
}

anvil {
    generateDaggerFactories = false
}

ksp {
    listOf(
        "fastInit",
        "ignoreProvisionKeyWildcards",
        "strictMultibindingValidation",
        "useBindingGraphFix",
    ).forEach {
        arg("-Adagger.$it", "ENABLED")
    }
}

dependencies {
    add("implementation", versionCatalog.findLibrary("dagger").get())
    add("ksp", versionCatalog.findLibrary("dagger.compiler").get())
}

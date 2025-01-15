/*
 * Copyright (c) 2023-2025, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package ru.pixnews.gradle.project.di

import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures anvil with generateDaggerFactories turned on
 */
plugins {
    id("dev.zacsweers.anvil")
}

anvil {
    addOptionalAnnotations = true
    syncGeneratedSources = true
    generateDaggerFactories = true
    useKsp(
        contributesAndFactoryGeneration = true,
        componentMerging = true,
    )
}

dependencies {
    listOf(
        "pixnews-anvil-activity-generator",
        "pixnews-anvil-experiment-generator",
        "pixnews-anvil-initializer-generator",
        "pixnews-anvil-test-generator",
        "pixnews-anvil-viewmodel-generator",
        "pixnews-anvil-workmanager-generator",
    ).forEach {
        add("anvil", versionCatalog.findLibrary(it).get())
        add("ksp", versionCatalog.findLibrary(it).get()) // TODO: check
    }
}

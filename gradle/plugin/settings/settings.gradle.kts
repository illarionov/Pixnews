/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories.gradlePluginPortal()
}

pluginManagement {
    includeBuild("../base/kotlindsl")
    includeBuild("../base/build-parameters")
}

rootProject.name = "gradle-settings-plugins"
rootProject.buildFileName = "settings-plugins.gradle.kts"

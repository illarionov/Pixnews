/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories.gradlePluginPortal()
    versionCatalogs {
        create("libs") {
            from(files("../../../libs.versions.toml"))
        }
    }
}

rootProject.name = "gradle-billofmaterials"
rootProject.buildFileName = "billofmaterials.gradle.kts"

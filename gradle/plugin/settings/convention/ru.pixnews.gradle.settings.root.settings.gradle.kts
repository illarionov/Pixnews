/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

/**
 * Base settings convention plugin for the use in the main android application and
 * in library modules
 */
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
    id("ru.pixnews.gradle.settings.common")
    id("ru.pixnews.gradle.settings.repositories")
    id("ru.pixnews.gradle.settings.gradle-enterprise")
}

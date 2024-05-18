/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.base

/**
 * Convention plugin that adds additional directory to the gradle-dsl source directory list
 * Workaround for https://github.com/gradle/gradle/issues/21052
 */
plugins {
    `java-gradle-plugin`
}

sourceSets {
    main {
        java.srcDir("convention")
    }
}

// apply kotlin-dsl plugin last, because it erroneously fetches source dirs eagerly.
apply(plugin = "org.gradle.kotlin.kotlin-dsl")

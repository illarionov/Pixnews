/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.lint

import com.android.build.api.dsl.Lint

internal fun Lint.configureCommonAndroidLint() {
    quiet = false
    ignoreWarnings = false
    htmlReport = true
    xmlReport = true
    sarifReport = true
    checkDependencies = false
    ignoreTestSources = true

    disable += "ObsoleteSdkInt"
    informational += "GradleDependency"
}

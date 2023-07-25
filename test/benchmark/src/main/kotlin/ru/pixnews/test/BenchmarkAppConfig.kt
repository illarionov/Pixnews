/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.test

import ru.pixnews.test.benchmark.BuildConfig

@Suppress("CONSTANT_UPPERCASE")
object BenchmarkAppConfig {
    const val targetPackage: String = "ru.pixnews${BuildConfig.APP_BUILD_TYPE_SUFFIX}"
}

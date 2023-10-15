/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

public fun Context.getLocalizedResources(locale: Locale): Resources {
        val config: Configuration = Configuration(resources.configuration).apply {
        setLocale(locale)
    }
    return createConfigurationContext(config).resources
}

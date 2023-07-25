/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import java.util.Locale

/**
 * A composable function that returns the default [Locale]. It will be recomposed when the
 * `Configuration` gets updated.
 */
@Composable
@ReadOnlyComposable
public fun defaultLocale(): Locale {
    return ConfigurationCompat.getLocales(LocalConfiguration.current).get(0) ?: Locale.getDefault()
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.ui.tooling.debuglayout.ruler

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

internal val markerTextFormatter = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ROOT)).apply {
    maximumFractionDigits = 1
}

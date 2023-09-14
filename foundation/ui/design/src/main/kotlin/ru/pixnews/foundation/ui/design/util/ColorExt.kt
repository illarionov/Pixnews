/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.util

import androidx.compose.ui.graphics.Color as ComposeColor
import ru.pixnews.domain.model.util.Color as PixnewsColor

public fun PixnewsColor.composeColor(): ComposeColor {
    return if (this == PixnewsColor.Unspecified) {
        ComposeColor.Unspecified
    } else {
        ComposeColor(argbValue())
    }
}

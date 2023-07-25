/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil.mapper

import coil.size.Dimension
import coil.size.Dimension.Pixels
import coil.size.Dimension.Undefined
import ru.pixnews.domain.model.util.Dimension as PixnewsDimension

internal fun Dimension.toDimensionModel(): PixnewsDimension {
    return when (this) {
        is Pixels -> PixnewsDimension(this.px.toUInt())
        Undefined -> PixnewsDimension.Undefined
    }
}

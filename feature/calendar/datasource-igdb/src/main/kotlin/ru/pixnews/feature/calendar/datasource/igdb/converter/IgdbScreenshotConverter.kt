/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.feature.calendar.datasource.igdb.model.IgdbImageUrl
import ru.pixnews.igdbclient.model.Screenshot as IgdbScreenshot

internal fun IgdbScreenshot.toIgdbImageUrl(): ImageUrl {
    requireFieldInitialized("screenshots.image_id", image_id)
    return IgdbImageUrl(
        igdbImageId = this.image_id,
        animated = this.animated,
        size = if (this.width != 0 && this.height != 0) {
            CanvasSize(this.width.toUInt(), this.height.toUInt())
        } else {
            null
        },
    )
}

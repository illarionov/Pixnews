/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.model.url

import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Dimension
import ru.pixnews.domain.model.util.Dimension.Undefined
import ru.pixnews.igdbclient.model.IgdbImageFormat.WEBP
import ru.pixnews.igdbclient.model.IgdbImageSize.H1080P
import ru.pixnews.igdbclient.model.IgdbImageSize.THUMB
import ru.pixnews.igdbclient.util.igdbImageUrl

internal data class IgdbImageUrl(
    val igdbImageId: String,
    val animated: Boolean,
    override val size: CanvasSize?,
) : ImageUrl {
    init {
        check(igdbImageId.isNotEmpty()) { "igdbImageId should be set" }
    }

    override fun getUrl(width: Dimension, height: Dimension): String {
        if (size == null || (width is Undefined && height is Undefined)) {
            return igdbImageUrl(
                imageId = igdbImageId,
                imageFormat = WEBP,
                size2x = false,
                imageSize = THUMB,
            )
        }

        // TODO: more precise size
        return igdbImageUrl(
            imageId = igdbImageId,
            imageFormat = WEBP,
            size2x = true,
            imageSize = H1080P,
        )
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.feature.calendar.datasource.igdb.model.url.IgdbImageUrl
import ru.pixnews.igdbclient.model.CompanyLogo as IgdbCompanyLogo

internal fun IgdbCompanyLogo.toIgdbImageUrl(): ImageUrl = IgdbImageUrl(
    igdbImageId = requireFieldInitialized("logo.image_id", image_id),
    animated = this.animated,
    size = if (width != 0 && height != 0) {
        CanvasSize(width = width.toUInt(), height = height.toUInt())
    } else {
        null
    },
)

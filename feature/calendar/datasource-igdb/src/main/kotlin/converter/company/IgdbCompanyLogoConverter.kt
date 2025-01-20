/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.company

import at.released.igdbclient.dsl.field.CompanyLogoFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.CompanyLogo
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.url.IgdbImageUrl

internal object IgdbCompanyLogoConverter {
    fun getRequiredFields(from: CompanyLogoFieldDsl): List<IgdbRequestField<*>> = with(from) {
        listOf(
            image_id,
            animated,
            width,
            height,
        )
    }

    fun convert(igdbObject: CompanyLogo): ImageUrl = with(igdbObject) {
        IgdbImageUrl(
            igdbImageId = requireFieldInitialized("logo.image_id", image_id),
            animated = this.animated,
            size = if (width != 0 && height != 0) {
                CanvasSize(width = width.toUInt(), height = height.toUInt())
            } else {
                null
            },
        )
    }
}

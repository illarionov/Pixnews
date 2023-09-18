/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.scheme

import ru.pixnews.igdbclient.model.AgeRating

public enum class IgdbAgeRatingField(
    override val igdbName: String,
) : IgdbField<AgeRating> {
    ID("id"),
    CATEGORY("category"),
    CONTENT_DESCRIPTIONS("content_descriptions"),
    RATING("rating"),
    RATING_COVER_URL("rating_cover_url"),
    SYNOPSIS("synopsis"),
    CHECKSUM("checksum"),
}

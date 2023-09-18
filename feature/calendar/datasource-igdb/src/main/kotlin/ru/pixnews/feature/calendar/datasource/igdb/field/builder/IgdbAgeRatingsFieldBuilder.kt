/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.builder

import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbAgeRatingField
import ru.pixnews.igdbclient.model.AgeRating

public fun AgeRating.Companion.field(): IgdbAgeRatingsFieldBuilder = IgdbAgeRatingsFieldBuilder()

@Suppress("VariableNaming")
@IgdbFieldDsl
public class IgdbAgeRatingsFieldBuilder internal constructor(
    parent: IgdbRequestField<*>? = null,
) : IgdbRequestFieldBuilder<AgeRating>(parent) {
    public val id: IgdbRequestField<AgeRating> get() = IgdbRequestField(IgdbAgeRatingField.ID, parent)
    public val category: IgdbRequestField<AgeRating> get() = IgdbRequestField(IgdbAgeRatingField.CATEGORY, parent)
    public val content_descriptions: IgdbRequestField<AgeRating>
        get() = IgdbRequestField(
            IgdbAgeRatingField.CONTENT_DESCRIPTIONS,
            parent,
        )
    public val rating: IgdbRequestField<AgeRating> get() = IgdbRequestField(IgdbAgeRatingField.RATING, parent)
    public val rating_cover_url: IgdbRequestField<AgeRating>
        get() = IgdbRequestField(
            IgdbAgeRatingField.RATING_COVER_URL,
            parent,
        )
    public val synopsis: IgdbRequestField<AgeRating> get() = IgdbRequestField(IgdbAgeRatingField.SYNOPSIS, parent)
    public val checksum: IgdbRequestField<AgeRating> get() = IgdbRequestField(IgdbAgeRatingField.CHECKSUM, parent)
}

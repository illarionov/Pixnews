/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.builder

import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.igdbclient.model.AgeRating

public fun AgeRating.Companion.field(): IgdbAgeRatingsFieldBuilder = IgdbAgeRatingsFieldBuilder()

@Suppress("VariableNaming")
@IgdbFieldDsl
public class IgdbAgeRatingsFieldBuilder internal constructor(
    parent: IgdbRequestField<*>? = null,
) : IgdbRequestFieldBuilder<AgeRating>(parent) {
    public val id: IgdbRequestField<AgeRating> get() = named("id")
    public val category: IgdbRequestField<AgeRating> = named("category")
    public val content_descriptions: IgdbRequestField<AgeRating> = named("content_descriptions")
    public val rating: IgdbRequestField<AgeRating> get() = named("rating")
    public val rating_cover_url: IgdbRequestField<AgeRating> get() = named("rating_cover_url")
    public val synopsis: IgdbRequestField<AgeRating> get() = named("synopsis")
    public val checksum: IgdbRequestField<AgeRating> get() = named("checksum")

    private fun named(igdbFieldName: String): IgdbRequestField<AgeRating> = IgdbRequestField(
        igdbFieldName,
        AgeRating::class,
        parent,
    )
}

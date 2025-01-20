/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import ru.pixnews.domain.model.game.AverageRating
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId.Companion.asIgdbGameId

internal object IgdbGameRatingsConverter : IgdbGameFieldConverter<RatingsSummary> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.id,
        from.rating,
        from.rating_count,
    )

    @Suppress("MagicNumber", "FLOAT_IN_ACCURATE_CALCULATIONS")
    override fun convert(game: Game): RatingsSummary = RatingsSummary(
        gameId = game.id.asIgdbGameId(),
        numberOfVotes = game.rating_count.toULong(),
        averageRating = if (game.rating >= 1.0) {
            AverageRating((game.rating / 10.0).toFloat().coerceIn(1f..10f))
        } else {
            null
        },
        distribution = null,
    )
}

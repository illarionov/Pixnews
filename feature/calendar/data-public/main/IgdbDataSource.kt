/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data

import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.model.GameModeIgdbDto
import ru.pixnews.library.functional.network.NetworkResult

public interface IgdbDataSource {
    public suspend fun fetchUpcomingReleases(
        startDate: Instant,
        requiredFields: Set<GameField>,
        offset: Int = 0,
        limit: Int = 100,
    ): NetworkResult<List<Game>>

    public suspend fun getGameModes(
        updatedLaterThan: Instant?,
        offset: Int = 0,
        limit: Int = 100,
    ): NetworkResult<List<GameModeIgdbDto>>
}

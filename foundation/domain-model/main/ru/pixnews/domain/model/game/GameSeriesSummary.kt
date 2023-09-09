/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.datasource.DataSource
import ru.pixnews.domain.model.id.GameSeriesId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.Ref

public data class GameSeriesSummary(
    val id: GameSeriesId,
    val name: Localized<String>,
    val totalGamesCount: UInt?,
    val games: ImmutableList<Ref<Game>>,
    val attribution: ImmutableList<DataSource>,
)

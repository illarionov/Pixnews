/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.locale.Localized
import javax.sql.DataSource

public data class GameSeriesSummary(
    val id: GameSeriesId,
    val name: Localized<String>,
    val totalGamesCount: UInt?,
    val games: ImmutableList<Game>?,
    val attribution: DataSource,
)
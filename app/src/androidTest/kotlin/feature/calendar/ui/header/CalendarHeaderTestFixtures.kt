/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.header

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.datetime.LocalDate
import ru.pixnews.feature.calendar.model.GameListFilterChip
import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.SELECTED
import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.UNSELECTED
import ru.pixnews.feature.calendar.model.GamesOnDay

internal object CalendarHeaderTestFixtures {
    internal val activeDate = LocalDate(2023, 3, 4)
    internal val gamesSummaryOnActiveDate: ImmutableMap<LocalDate, GamesOnDay> =
        persistentHashMapOf(
            LocalDate(2023, 2, 27) to GamesOnDay(
                hasFollowedGames = true,
                hasReleases = true,
            ),
            LocalDate(2023, 2, 28) to GamesOnDay(),
            LocalDate(2023, 3, 1) to GamesOnDay(
                hasFollowedGames = true,
            ),
            LocalDate(2023, 3, 3) to GamesOnDay(
                hasReleases = true,
            ),
            LocalDate(2023, 3, 4) to GamesOnDay(
                hasFollowedGames = true,
                hasReleases = true,
            ),
        )
    internal val chips = listOf(
        GameListFilterChip(id = "popular", label = "Popular", SELECTED),
        GameListFilterChip(id = "under12", label = "For kids", SELECTED),
        GameListFilterChip(id = "rpg", label = "RPG", UNSELECTED),
    )
}

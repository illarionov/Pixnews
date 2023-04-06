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
package ru.pixnews.feature.calendar

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.datetime.LocalDate
import ru.pixnews.feature.calendar.model.GamesOnDay

internal object CalendarHeaderTestFixtures {
    internal val activeDate = LocalDate(2023, 3, 4)
    internal val gamesSummaryOnActiveDate: ImmutableMap<LocalDate, GamesOnDay> = persistentHashMapOf(
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
        PreviewFixtures.FilterChip.popularChip,
        PreviewFixtures.FilterChip.forKidsUnder12Chip,
        PreviewFixtures.FilterChip.rpgChip,
    )
}

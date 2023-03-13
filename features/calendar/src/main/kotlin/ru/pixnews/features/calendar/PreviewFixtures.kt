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
package ru.pixnews.features.calendar

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GamePlatform.PlayStation4
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxOne
import ru.pixnews.domain.model.game.game.beyondGoodEvil2
import ru.pixnews.domain.model.game.game.gta6
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.project007
import ru.pixnews.domain.model.game.game.sims5
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.domain.model.game.game.smalland
import ru.pixnews.domain.model.game.game.starWarsEclipse
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.features.calendar.model.CalendarListTitle
import ru.pixnews.features.calendar.model.CalendarModel
import ru.pixnews.features.calendar.model.CalendarScreenState
import ru.pixnews.features.calendar.model.DEFAULT_CALENDAR_MODEL
import ru.pixnews.features.calendar.model.GameListFilterChip
import ru.pixnews.features.calendar.model.GameListFilterChipStyle.SELECTED
import ru.pixnews.features.calendar.model.GameListFilterChipStyle.UNSELECTED
import ru.pixnews.features.calendar.model.GamesOnDay
import ru.pixnews.features.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.features.calendar.model.toCalendarListItem
import ru.pixnews.features.calendar.model.toMajorReleaseCarouselItemUiModel
import ru.pixnews.foundation.ui.imageloader.coil.tooling.withDebug
import kotlin.time.Duration.Companion.seconds

internal object PreviewFixtures {
    val DummyCalendarModel: CalendarModel = DEFAULT_CALENDAR_MODEL
    val previewActiveDate: LocalDate = LocalDate(2023, 2, 28)
    val gamesSummaryOnActiveDate: ImmutableMap<LocalDate, GamesOnDay> = persistentHashMapOf(
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
    val previewSuccessState: CalendarScreenState.Success = CalendarScreenState.Success(
        majorReleases = persistentListOf(
            MajorRelease.slimerancher2.copy(
                cover = Release.slimerancher2.cover?.withDebug(loadingDelay = 10.seconds),
            ),
            MajorRelease.smalland,
            MajorRelease.halflife3,
            MajorRelease.beyondgoodandevil2,
            MajorRelease.thelostwild,
            MajorRelease.thesims5,
            MajorRelease.hytale,
            MajorRelease.starwarseclipse,
            MajorRelease.gta6,
            MajorRelease.project007,
        ),
        games = persistentListOf(
            CalendarListTitle(title = "1 January 2024"),
            Release.slimerancher2.copy(
                cover = Release.slimerancher2.cover?.withDebug(loadingDelay = 10.seconds),
            ),
            Release.hytale,
            CalendarListTitle(title = "2 January 2024"),
            Release.gta6,
            Release.thelostwild,
            CalendarListTitle(title = "TBD January 2024"),
            Release.thesims5,
            Release.beyondgoodandevil2,
            Release.starwarseclipse,
            CalendarListTitle(title = "TBD Early 2024"),
            Release.halflife3,
            CalendarListTitle(title = "TBD"),
            Release.smalland,
            Release.project007,
        ),
    )

    object FilterChip {
        val popularChip = GameListFilterChip(id = "popular", label = "Popular", SELECTED)
        val forKidsUnder12Chip = GameListFilterChip(id = "under12", label = "For kids", SELECTED)
        val rpgChip = GameListFilterChip(id = "rpg", label = "RPG", UNSELECTED)
        val sampleChips = listOf(popularChip, forKidsUnder12Chip, rpgChip)
    }

    object Release {
        val halflife3 = GameFixtures.halfLife3.toCalendarListItem()
        val gta6 = GameFixtures.gta6.toCalendarListItem()
        val hytale = GameFixtures.hytale.toCalendarListItem()
        val thesims5 = GameFixtures.sims5.toCalendarListItem()
        val beyondgoodandevil2 = GameFixtures.beyondGoodEvil2.toCalendarListItem()
        val starwarseclipse = GameFixtures.starWarsEclipse.toCalendarListItem()
        val slimerancher2 = GameFixtures.slimeRancher2.toCalendarListItem()
        val smalland = GameFixtures.smalland.toCalendarListItem()
        val project007 = GameFixtures.project007.toCalendarListItem()
        val thelostwild = GameFixtures.theLostWild.toCalendarListItem()
    }

    object MajorRelease {
        val beyondgoodandevil2 = GameFixtures.beyondGoodEvil2.toMajorReleaseCarouselItemUiModel().copy(
            favourite = true,
        )
        val gta6 = GameFixtures.gta6.toMajorReleaseCarouselItemUiModel().copy(
            favourite = true,
        )
        val halflife3 = GameFixtures.halfLife3.toMajorReleaseCarouselItemUiModel()
        val hytale = GameFixtures.hytale.toMajorReleaseCarouselItemUiModel()
        val project007 = GameFixtures.project007.toMajorReleaseCarouselItemUiModel()
        val slimerancher2 = GameFixtures.slimeRancher2.toMajorReleaseCarouselItemUiModel()
        val smalland = GameFixtures.smalland.toMajorReleaseCarouselItemUiModel().copy(
            favourite = true,
        )
        val starwarseclipse = GameFixtures.starWarsEclipse.toMajorReleaseCarouselItemUiModel()
        val thelostwild = GameFixtures.theLostWild.toMajorReleaseCarouselItemUiModel()
        val thesims5 = GameFixtures.sims5.toMajorReleaseCarouselItemUiModel()
        val battlefieldbadcompany3 = MajorReleaseCarouselItemUiModel(
            gameId = GameId(id = "battlefield-bad-company-3"),
            title = "Battlefield: Bad Company 3",
            cover = null,
            platforms = persistentSetOf(),
            favourite = false,
        )
        val dokev = MajorReleaseCarouselItemUiModel(
            gameId = GameId(id = "dokev"),
            title = "DokeV",
            cover = DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/co56d8.png",
                size = CanvasSize(width = 264U, height = 352U),
            ),
            platforms = persistentSetOf(Windows, PlayStation4, XboxOne),
            favourite = false,
        )
    }
}

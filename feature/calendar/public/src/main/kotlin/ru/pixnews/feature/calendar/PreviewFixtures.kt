/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameFixtures
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
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.feature.calendar.converter.UpcomingGameListConverter.toCalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded
import ru.pixnews.feature.calendar.model.GameListFilterChip
import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.SELECTED
import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.UNSELECTED
import ru.pixnews.feature.calendar.model.GamesOnDay
import ru.pixnews.feature.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.feature.calendar.model.toMajorReleaseCarouselItemUiModel
import ru.pixnews.foundation.ui.imageloader.coil.tooling.OverrideResult
import ru.pixnews.foundation.ui.imageloader.coil.tooling.withDebug
import java.time.Month.FEBRUARY
import java.time.Month.JANUARY
import kotlin.time.Duration.Companion.seconds

internal object PreviewFixtures {
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
    val previewSuccessState: CalendarScreenStateLoaded.Success = CalendarScreenStateLoaded.Success(
        majorReleases = persistentListOf(
            MajorRelease.slimerancher2.copy(
                cover = Release.slimerancher2.cover?.withDebug(loadingDelay = 10.seconds),
            ),
            MajorRelease.smalland.copy(
                cover = Release.smalland.cover?.withDebug(overrideResult = OverrideResult.Error()),
            ),
            MajorRelease.halflife3,
            MajorRelease.beyondgoodandevil2,
            MajorRelease.thelostwild,
            MajorRelease.thesims5,
            MajorRelease.hytale,
            MajorRelease.starwarseclipse,
            MajorRelease.gta6,
            MajorRelease.project007,
        ),
    )

    object UpcomingReleases {
        val list = persistentListOf(
            CalendarListTitle(CalendarDateGroup.jan1st2024),
            Release.slimerancher2.copy(
                cover = Release.slimerancher2.cover?.withDebug(loadingDelay = 10.seconds),
            ),
            Release.hytale,
            CalendarListTitle(CalendarDateGroup.jan2st2024),
            Release.gta6,
            Release.thelostwild,
            CalendarListTitle(CalendarDateGroup.tbdJan2024),
            Release.thesims5,
            Release.beyondgoodandevil2,
            Release.starwarseclipse,
            CalendarListTitle(CalendarDateGroup.tbdFeb2024),
            CalendarListTitle(CalendarDateGroup.tbdMar2024),
            CalendarListTitle(CalendarDateGroup.tbdEarly2024),
            Release.halflife3.copy(
                cover = Release.halflife3.cover?.withDebug(
                    loadingDelay = 5.seconds,
                    overrideResult = OverrideResult.Error(),
                ),
            ),
            CalendarListTitle(CalendarDateGroup.tbd),
            Release.smalland,
            Release.project007,
        )
        val successPagingData = PagingData.from(list)
        val initialLoadingPagingData: PagingData<CalendarListItem> = PagingData.from(
            data = emptyList(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
        )
    }

    object CalendarDateGroup {
        @Suppress("UnusedPrivateProperty")
        private val requestTimeLocalJun1 = LocalDateTime(2024, 1, 1, 22, 23, 24, 0)
            .toInstant(TimeZone.of("UTC+3"))

        // title = "1 January 2024"
        val jan1st2024 = Date.YearMonthDay(2024, JANUARY, 1)
        val jan2st2024 = Date.YearMonthDay(2024, JANUARY, 2)
        val tbdJan2024 = Date.YearMonth(2024, JANUARY)
        val tbdFeb2024 = Date.YearMonth(2024, FEBRUARY)
        val tbdMar2024 = Date.YearQuarter(2024, 1)
        val tbdEarly2024 = Date.Year(2024)
        val tbd = Date.Unknown()
    }

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

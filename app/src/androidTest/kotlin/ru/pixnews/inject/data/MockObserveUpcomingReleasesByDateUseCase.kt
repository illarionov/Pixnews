/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.data

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameFixtures
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
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.TBD
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.feature.calendar.domain.upcoming.DefaultObserveUpcomingReleasesByDateUseCase
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.network.NetworkRequestStatus
import java.time.Month.AUGUST
import java.time.Month.JUNE
import java.time.Month.MAY
import javax.inject.Inject

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
    replaces = [DefaultObserveUpcomingReleasesByDateUseCase::class],
)
class MockObserveUpcomingReleasesByDateUseCase @Inject constructor() : ObserveUpcomingReleasesByDateUseCase {
    override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<UpcomingReleasesResponse>> {
        return flowOf(
            NetworkRequestStatus.completeSuccess(
                UpcomingReleasesResponse(
                    requestTime = currentTime.toInstant(currentTimeZone),
                    requestedFields = requiredFields.toImmutableSet(),
                    games = releases,
                ),
            ),
        )
    }

    companion object UpcomingReleasesDateFixtures {
        val currentTime = LocalDateTime(2023, 5, 17, 10, 0, 0, 0)
        val currentTimeZone = TimeZone.of("UTC+3")

        // TODO
        val releases = persistentListOf(
            // 17 May 2023
            UpcomingRelease(
                // TODO: loading with debug
                game = GameFixtures.slimeRancher2.copy(
                    releaseDate = CurrentMonth.exactDateToday,
                ),
                group = FEW_DAYS,
            ),
            UpcomingRelease(GameFixtures.hytale.copy(releaseDate = CurrentMonth.exactDateToday), FEW_DAYS),

            // 18 May 2023
            UpcomingRelease(GameFixtures.gta6.copy(releaseDate = CurrentMonth.exactDateTomorrow), FEW_DAYS),
            UpcomingRelease(GameFixtures.theLostWild.copy(releaseDate = CurrentMonth.exactDateTomorrow), FEW_DAYS),

            // TBD May 2023
            UpcomingRelease(GameFixtures.starWarsEclipse.copy(releaseDate = CurrentMonth.approxDate), CURRENT_MONTH),

            // TBD 1 Quarter 2023
            UpcomingRelease(GameFixtures.sims5.copy(releaseDate = CurrentQuarter.exactDate), CURRENT_QUARTER),
            UpcomingRelease(
                GameFixtures.beyondGoodEvil2.copy(releaseDate = CurrentQuarter.approxDateMonth),
                CURRENT_QUARTER,
            ),

            // TBD: Current year
            UpcomingRelease(GameFixtures.halfLife3.copy(releaseDate = CurrentQuarter.approxDateMonth), CURRENT_YEAR),

            // Tbd
            UpcomingRelease(GameFixtures.smalland, TBD),
            UpcomingRelease(GameFixtures.project007, TBD),
        )

        object CurrentMonth {
            val exactDateToday = Date.YearMonthDay(2023, MAY, 17)
            val exactDateTomorrow = Date.YearMonthDay(2023, MAY, 18)
            val exactDateLater = Date.YearMonthDay(2023, MAY, 25)
            val approxDate = Date.YearMonth(2023, MAY)
        }

        object NextMonth {
            val exactDate = Date.YearMonthDay(2023, JUNE, 10)
            val approxDate = Date.YearMonth(2023, JUNE)
        }

        object CurrentQuarter {
            val exactDate = Date.YearMonthDay(2023, AUGUST, 11)
            val approxDateMonth = Date.YearMonth(2023, AUGUST)
            val approxDateQuarter = Date.YearQuarter(2023, 2)
        }
    }
}

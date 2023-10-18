/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.domain.upcoming

import arrow.core.Either
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.mapComplete
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkRequestStatus
import javax.inject.Inject

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
)
public class DefaultObserveUpcomingReleasesByDateUseCase(
    private val igdbRepository: IgdbRepository,
    private val clock: Clock,
    private val tzProvider: () -> TimeZone,
) : ObserveUpcomingReleasesByDateUseCase {
    @Inject
    public constructor(
        igdbRepository: IgdbRepository,
    ) : this(
        igdbRepository = igdbRepository,
        clock = System,
        tzProvider = TimeZone.Companion::currentSystemDefault,
    )

    public override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<UpcomingReleasesResponse>> {
        val now = clock.now()
        val startDate = now.getFirstDayOfMonth()
        val requiredFieldsSet = requiredFields.toImmutableSet()

        return igdbRepository.createUpcomingReleasesObservable(
            startDate = startDate,
            requiredFields = requiredFieldsSet,
        ).map { status: NetworkRequestStatus<List<Game>> ->
            status.mapComplete { successOrFailure: Either<NetworkRequestFailure<*>, List<Game>> ->
                successOrFailure.map { games ->
                    UpcomingReleasesResponse(now, requiredFieldsSet, games)
                }
            }
        }
    }

    private fun Instant.getFirstDayOfMonth(): Instant {
        val tz = tzProvider()
        val today = this.toLocalDateTime(tz).date
        val startOfMonth = LocalDate(today.year, today.month, 1)
        return startOfMonth.atStartOfDayIn(tz)
    }

    private operator fun UpcomingReleasesResponse.Companion.invoke(
        startDate: Instant,
        requiredFields: ImmutableSet<GameField>,
        games: List<Game>,
    ): UpcomingReleasesResponse = UpcomingReleasesResponse(
        requestTime = startDate,
        requestedFields = requiredFields,
        games = persistentListOf<UpcomingRelease>().builder().apply {
            games.forEach { game ->
                add(UpcomingRelease(game, CURRENT_MONTH))
            }
        }.build(),
    )

    public companion object {
        public interface IgdbRepository {
            public fun createUpcomingReleasesObservable(
                startDate: Instant,
                requiredFields: Set<GameField>,
            ): Flow<NetworkRequestStatus<List<Game>>>
        }
    }
}

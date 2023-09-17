/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.domain

import com.squareup.anvil.annotations.optional.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.todayIn
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.network.NetworkRequestStatus
import javax.inject.Inject

@SingleIn(AppScope::class)
public class DefaultObserveUpcomingReleasesByDateUseCase(
    private val igdbRepository: IgdbRepository,
    private val clock: Clock,
    private val tzProvider: () -> TimeZone,
) {
    @Inject
    public constructor(
        igdbRepository: IgdbRepository,
    ) : this(
        igdbRepository = igdbRepository,
        clock = Clock.System,
        tzProvider = TimeZone.Companion::currentSystemDefault,
    )

    public fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<List<Game>>> {
        return igdbRepository.createUpcomingReleasesObservable(
            startDate = clock.getFirstDayOfMonth(),
            requiredFields = requiredFields,
        )
    }

    private fun Clock.getFirstDayOfMonth(): Instant {
        val tz = tzProvider()
        val today = todayIn(tz)
        val startOfMonth = LocalDate(today.year, today.month, 1)
        return startOfMonth.atStartOfDayIn(tz)
    }

    public companion object {
        public interface IgdbRepository {
            public fun createUpcomingReleasesObservable(
                startDate: Instant,
                requiredFields: Set<GameField>,
            ): Flow<NetworkRequestStatus<List<Game>>>
        }
    }
}

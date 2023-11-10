/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.domain.upcoming

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.squareup.anvil.annotations.ContributesBinding
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
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
)
public class DefaultObserveUpcomingReleasesByDateUseCase(
    private val igdbPagingSourceFactory: IgdbPagingSource.Factory,
    private val clock: Clock,
    private val tzProvider: () -> TimeZone,
) : ObserveUpcomingReleasesByDateUseCase {
    @Inject
    public constructor(
        igdbPagingSourceFactory: IgdbPagingSource.Factory,
    ) : this(
        igdbPagingSourceFactory = igdbPagingSourceFactory,
        clock = System,
        tzProvider = TimeZone.Companion::currentSystemDefault,
    )

    public override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<PagingData<UpcomingRelease>> {
        val now = clock.now()
        val startDate = now.getFirstDayOfMonth()
        val requiredFieldsSet = requiredFields.toImmutableSet()

        val pager = Pager(
            initialKey = null,
            config = PagingConfig(pageSize = INITIAL_PAGING_SIZE),
            pagingSourceFactory = {
                igdbPagingSourceFactory.create(
                    startDate = startDate,
                    requiredFields = requiredFieldsSet,
                )
            },
        )

        return pager.flow
            .map { pagingData ->
                pagingData.map { game ->
                    UpcomingRelease(game, CURRENT_MONTH)
                }
            }
    }

    private fun Instant.getFirstDayOfMonth(): Instant {
        val tz = tzProvider()
        val today = this.toLocalDateTime(tz).date
        val startOfMonth = LocalDate(today.year, today.month, 1)
        return startOfMonth.atStartOfDayIn(tz)
    }

    public companion object {
        public const val INITIAL_PAGING_SIZE: Int = 10
    }
}

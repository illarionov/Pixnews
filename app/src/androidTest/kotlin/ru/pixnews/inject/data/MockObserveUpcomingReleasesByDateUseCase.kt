/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.data

import androidx.paging.PagingData
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.anvil.annotations.optional.SingleIn
import kotlinx.coroutines.flow.Flow
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.domain.upcoming.DefaultObserveUpcomingReleasesByDateUseCase
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
    replaces = [DefaultObserveUpcomingReleasesByDateUseCase::class],
)
@SingleIn(AppScope::class)
class MockObserveUpcomingReleasesByDateUseCase @Inject constructor() : ObserveUpcomingReleasesByDateUseCase {
    @get:Synchronized
    @set:Synchronized
    internal var createUpcomingReleasesObservableResponse: (
        requiredFields: Set<GameField>,
    ) -> Flow<PagingData<UpcomingRelease>> = NOT_INITIALIZED

    override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<PagingData<UpcomingRelease>> {
        return createUpcomingReleasesObservableResponse(requiredFields)
    }

    companion object UpcomingReleasesDateFixtures {
        internal val NOT_INITIALIZED: (
            requiredFields: Set<GameField>,
        ) -> Flow<PagingData<UpcomingRelease>> = { _ ->
            error("MockObserveUpcomingReleasesByDateUseCase not initialized")
        }
    }
}

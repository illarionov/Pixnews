/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.data

import androidx.paging.PagingData
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.anvil.annotations.optional.SingleIn
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase.UpcomingRelease
import ru.pixnews.feature.calendar.data.subdomain.upcoming.DefaultObserveUpcomingReleasesByDateUseCase
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

typealias UpcomingReleasesUseCaseResponse = (requiredFields: Set<GameField>) -> Flow<PagingData<UpcomingRelease>>

internal val NOT_INITIALIZED: UpcomingReleasesUseCaseResponse = { _ ->
    error("MockObserveUpcomingReleasesByDateUseCase not initialized")
}

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
    replaces = [DefaultObserveUpcomingReleasesByDateUseCase::class],
)
@SingleIn(AppScope::class)
class MockObserveUpcomingReleasesByDateUseCase @Inject constructor() : ObserveUpcomingReleasesByDateUseCase {
    @get:Synchronized
    @set:Synchronized
    private var createUpcomingReleasesObservableResponse: UpcomingReleasesUseCaseResponse = NOT_INITIALIZED

    @get:Synchronized
    @set:Synchronized
    private var responseInitialized: CompletableJob = Job()

    fun setInitializationComplete() {
        responseInitialized.complete()
    }

    @Synchronized
    fun reset(response: UpcomingReleasesUseCaseResponse = NOT_INITIALIZED) {
        responseInitialized.complete()
        createUpcomingReleasesObservableResponse = response
        responseInitialized = Job()
    }

    @Synchronized
    public fun setResponse(
        response: UpcomingReleasesUseCaseResponse,
        useCaseInitialized: Boolean = true,
    ) {
        createUpcomingReleasesObservableResponse = response
        if (useCaseInitialized) {
            setInitializationComplete()
        }
    }

    override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<PagingData<UpcomingRelease>> {
        return flow {
            responseInitialized.join()
            emitAll(createUpcomingReleasesObservableResponse(requiredFields))
        }
    }
}

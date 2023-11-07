/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.assumption

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.asPagingSourceFactory
import arrow.atomic.AtomicInt
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.inject.MockResourcesHolder
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase.UpcomingReleasesDateFixtures.NOT_INITIALIZED
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import java.net.NoRouteToHostException

class UpcomingReleaseUseCaseAssumptions : ExternalResource() {
    private lateinit var mockUseCase: MockObserveUpcomingReleasesByDateUseCase

    override fun apply(base: Statement?, description: Description?): Statement {
        mockUseCase = (PixnewsRootComponentHolder.appComponent as MockResourcesHolder).mockUseCase
        return super.apply(base, description)
    }

    override fun before() {
        super.before()
        assumeUpcomingGamesResponseDefaultGame()
    }

    override fun after() {
        super.after()
        resetMockUseCase()
    }

    fun assumeUpcomingGamesResponseDefaultGame() {
        mockUseCase.createUpcomingReleasesObservableResponse = { _ ->
            val pager = Pager(
                config = PagingConfig(10),
                initialKey = null,
                pagingSourceFactory = listOf(DEFAULT_UPCOMING_RELEASE).asPagingSourceFactory(),
            )
            pager.flow
        }
    }

    fun resetMockUseCase() {
        mockUseCase.createUpcomingReleasesObservableResponse = NOT_INITIALIZED
    }

    fun assumeUpcomingGamesResponseEmpty() {
        mockUseCase.createUpcomingReleasesObservableResponse = { _ ->
            Pager(
                config = PagingConfig(10),
                initialKey = null,
                pagingSourceFactory = listOf<UpcomingRelease>().asPagingSourceFactory(),
            ).flow
        }
    }

    fun assumeUpcomingGamesResponseInitialLoading() {
        mockUseCase.createUpcomingReleasesObservableResponse = { requiredFields -> emptyFlow() }
    }

    fun assumeUpcomingReleasesSuccessfully(
        releases: List<UpcomingRelease>,
    ) {
        mockUseCase.createUpcomingReleasesObservableResponse = { _ ->
            Pager(
                config = PagingConfig(10),
                initialKey = null,
                pagingSourceFactory = releases.asPagingSourceFactory(),
            ).flow
        }
    }

    fun assumeUpcomingGamesResponseFailure(
        error: NetworkRequestFailure<*> = NetworkRequestFailure.NetworkFailure(NoRouteToHostException()),
    ) {
        // TODO: better implementation
        mockUseCase.createUpcomingReleasesObservableResponse = { requiredFields ->
            Pager(
                config = PagingConfig(10),
                initialKey = null,
                pagingSourceFactory = {
                    object : PagingSource<Int, UpcomingRelease>() {
                        override fun getRefreshKey(state: PagingState<Int, UpcomingRelease>): Int? = null

                        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingRelease> {
                            return LoadResult.Error(NetworkRequestFailureException(error))
                        }
                    }
                },
            ).flow
        }
    }

    fun assumeUpcomingReleasesSuccessfullyThenLoading(
        releases: List<UpcomingRelease>,
    ) {
        val successPagingSourceFactory = releases.asPagingSourceFactory()
        mockUseCase.createUpcomingReleasesObservableResponse = { _ ->
            Pager(
                config = PagingConfig(2, enablePlaceholders = false),
                initialKey = null,
                pagingSourceFactory = { SuccessThenLoadingPagingSource(successPagingSourceFactory()) },
            ).flow
        }
    }

    private class SuccessThenLoadingPagingSource(
        private val delegate: PagingSource<Int, UpcomingRelease>,
    ) : PagingSource<Int, UpcomingRelease>() {
        var requestNo = AtomicInt(0)
        override fun getRefreshKey(state: PagingState<Int, UpcomingRelease>): Int? = delegate.getRefreshKey(state)

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingRelease> {
            return when (requestNo.incrementAndGet()) {
                1 -> delegate.load(params)
                else -> awaitCancellation()
            }
        }
    }

    companion object {
        val DEFAULT_TIME = LocalDateTime(2023, 5, 17, 10, 0, 0, 0)
        val DEFAULT_TIME_ZONE = TimeZone.of("UTC+3")
        val DEFAULT_UPCOMING_RELEASE = UpcomingRelease(
            // TODO: loading with debug
            game = GameFixtures.slimeRancher2.copy(
                releaseDate = Date.YearMonthDay(2023, Month.MAY, 17),
            ),
            group = FEW_DAYS,
        )
    }
}

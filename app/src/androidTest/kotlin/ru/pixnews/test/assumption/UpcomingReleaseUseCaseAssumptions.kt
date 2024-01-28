/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.assumption

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.asPagingSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.datetime.Month
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase.UpcomingRelease
import ru.pixnews.inject.MockResourcesHolder
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import java.net.NoRouteToHostException

class UpcomingReleaseUseCaseAssumptions(
    private val autoInitialize: Boolean = true,
) : ExternalResource() {
    private lateinit var mockUseCase: MockObserveUpcomingReleasesByDateUseCase

    override fun apply(base: Statement?, description: Description?): Statement {
        mockUseCase = (PixnewsRootComponentHolder.appComponent as MockResourcesHolder).mockUseCase
        return super.apply(base, description)
    }

    override fun before() {
        super.before()
        if (autoInitialize) {
            mockUseCase.setResponse(response = SINGLE_GAME_RESPONSE)
        }
    }

    override fun after() {
        super.after()
        resetMockUseCase()
    }

    fun assumeUpcomingGamesResponseDefaultGame() {
        mockUseCase.setResponse(SINGLE_GAME_RESPONSE)
    }

    fun resetMockUseCase() {
        mockUseCase.reset()
    }

    fun assumeUpcomingGamesResponseEmpty() {
        mockUseCase.setResponse(
            { _ ->
                Pager(
                    config = PagingConfig(10),
                    initialKey = null,
                    pagingSourceFactory = listOf<UpcomingRelease>().asPagingSourceFactory(),
                ).flow
            },
        )
    }

    fun assumeUpcomingGamesResponseInitialLoading() {
        mockUseCase.setResponse({ _ -> emptyFlow() })
    }

    fun assumeUpcomingReleasesSuccessfully(
        releases: List<UpcomingRelease>,
    ) {
        mockUseCase.setResponse(
            { _ ->
                Pager(
                    config = PagingConfig(10),
                    initialKey = null,
                    pagingSourceFactory = releases.asPagingSourceFactory(),
                ).flow
            },
        )
    }

    fun assumeUpcomingGamesResponseFailure(
        error: NetworkRequestFailure<*> = NetworkRequestFailure.NetworkFailure(NoRouteToHostException()),
    ) {
        // TODO: better implementation
        mockUseCase.setResponse(
            { _ ->
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
            },
        )
    }

    companion object {
        private val DEFAULT_UPCOMING_RELEASE = UpcomingRelease(
            game = GameFixtures.slimeRancher2.copy(
                releaseDate = Date.YearMonthDay(2023, Month.MAY, 17),
            ),
            group = FEW_DAYS,
        )
        private val SINGLE_GAME_RESPONSE: (
            requiredFields: Set<GameField>,
        ) -> Flow<PagingData<UpcomingRelease>> = { _ ->
            val pager = Pager(
                config = PagingConfig(10),
                initialKey = null,
                pagingSourceFactory = listOf(DEFAULT_UPCOMING_RELEASE).asPagingSourceFactory(),
            )
            pager.flow
        }
    }
}

/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.repository.upcoming

import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.testing.TestPager
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameField.Id
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.data.IgdbDataSource
import ru.pixnews.feature.calendar.data.model.GameModeIgdbDto
import ru.pixnews.feature.calendar.data.repository.upcoming.DefaultUpcomingReleaseRepositoryTest.FakeTracingDataSource.FetchUpcomingReleasesRequest
import ru.pixnews.library.functional.completeFailure
import ru.pixnews.library.functional.completeSuccess
import ru.pixnews.library.functional.network.NetworkRequestFailure.ApiFailure
import ru.pixnews.library.functional.network.NetworkRequestFailure.NetworkFailure
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import ru.pixnews.library.functional.network.NetworkResult
import ru.pixnews.library.test.MainCoroutineExtension
import java.net.NoRouteToHostException
import java.util.Collections

class DefaultUpcomingReleaseRepositoryTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt = MainCoroutineExtension()
    private val dataSource = FakeTracingDataSource()
    private val startDate = Instant.parse("2024-11-01T00:00:00Z")
    private val pagingSource = DefaultUpcomingReleaseRepository(dataSource)
        .createUpcomingReleasesPagingSource(
            startDate = startDate,
            requiredFields = GAME_LIST_REQUIRED_FIELDS,
        )
    private val pager = TestPager(PAGING_CONFIG, pagingSource)

    @Test
    fun `load() should load initial data`() = coroutinesExt.runTest {
        val result = pager.refresh() as Page

        result.data.map { it.id.toString() }.shouldContainExactly((1..15).map { "igdb-$it" })
        result.nextKey shouldBe IgdbPagingSourceKey(15)
        result.prevKey shouldBe null

        dataSource.requests.shouldContainExactly(
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 0, 15),
        )
    }

    @Test
    fun `load() should load additional data`() = coroutinesExt.runTest {
        val result = with(pager) {
            refresh()
            append()
            append()
        } as Page

        result.data.map { it.id.toString() }
            .shouldContainExactly((21..25).map { "igdb-$it" })
        result.nextKey shouldBe IgdbPagingSourceKey(25)
        result.prevKey shouldBe null
        dataSource.requests.shouldContainExactly(
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 0, 15),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 15, 5),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 20, 5),
        )
    }

    @Test
    fun `load() should stop on no data`() = coroutinesExt.runTest {
        val result = with(pager) {
            refresh()
            append()
            append()
            append()
            append()
        } as Page

        result.data.shouldBeEmpty()
        result.nextKey shouldBe null
        result.prevKey shouldBe null
        dataSource.requests.shouldContainExactly(
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 0, 15),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 15, 5),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 20, 5),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 25, 5),
            FetchUpcomingReleasesRequest(startDate, GAME_LIST_REQUIRED_FIELDS, 30, 5),
        )
    }

    @ParameterizedTest
    @CsvSource(
        "0,0",
        "7,0",
        "15,15",
        "17,15",
    )
    fun `getRefreshKey() should return correct key`(
        anchorPosition: Int,
        expectedOffset: Int,
    ) = coroutinesExt.runTest {
        with(pager) {
            refresh()
            append()
            append()
        }
        val pagingState = pager.getPagingState(anchorPosition)

        val refreshKey = pagingSource.getRefreshKey(pagingState)

        refreshKey shouldBe IgdbPagingSourceKey(expectedOffset)
    }

    @Test
    fun `load() should return error on error`() = coroutinesExt.runTest {
        dataSource.mockResponse = { _, _, _, _ ->
            NetworkFailure(NoRouteToHostException()).completeFailure()
        }
        val result = pager.refresh() as Error
        result.throwable.run {
            shouldBeInstanceOf<NetworkRequestFailureException>()
            failure.shouldBeInstanceOf<NetworkFailure>()
        }
    }

    private class FakeTracingDataSource : IgdbDataSource {
        @Volatile
        var mockResponse: (
            startDate: Instant,
            requiredFields: Set<GameField>,
            offset: Int,
            limit: Int,
        ) -> NetworkResult<List<Game>> = DEFAULT_RESPONSE
        val requests: MutableList<FetchUpcomingReleasesRequest> =
            Collections.synchronizedList(mutableListOf())

        override suspend fun fetchUpcomingReleases(
            startDate: Instant,
            requiredFields: Set<GameField>,
            offset: Int,
            limit: Int,
        ): NetworkResult<List<Game>> {
            requests.add(
                FetchUpcomingReleasesRequest(
                    startDate = startDate,
                    requiredFields = requiredFields,
                    offset = offset,
                    limit = limit,
                ),
            )
            return mockResponse(startDate, requiredFields, offset, limit)
        }

        override suspend fun getGameModes(
            updatedSince: Instant?,
            offset: Int,
            limit: Int,
        ): NetworkResult<List<GameModeIgdbDto>> {
            error("Not implemented")
        }

        data class FetchUpcomingReleasesRequest(
            val startDate: Instant,
            val requiredFields: Set<GameField>,
            val offset: Int,
            val limit: Int,
        )
    }

    private companion object {
        val GAME_LIST_REQUIRED_FIELDS = persistentSetOf(Id)
        val MOCK_GAMES = (1..30).map {
            GameFixtures.halfLife3.copy(id = GameId("igdb-$it"))
        }
        val DEFAULT_RESPONSE: (Instant, Set<GameField>, Int, Int) -> NetworkResult<List<Game>> =
            { _, _, offset, limit ->
                try {
                    MOCK_GAMES.subList(
                        offset,
                        (offset + limit).coerceAtMost(MOCK_GAMES.size),
                    ).completeSuccess()
                } catch (e: Exception) {
                    ApiFailure(e).completeFailure()
                }
            }
        val PAGING_CONFIG = PagingConfig(pageSize = 5)
    }
}

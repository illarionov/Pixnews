/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.feature.calendar.model.CALENDAR_LIST_ITEM_GAME_FIELDS
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded
import ru.pixnews.feature.calendar.model.InitialLoad
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkRequestStatus
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers
import java.net.NoRouteToHostException

class CalendarViewModelTest {
    private val logger = TestingLoggers.consoleLogger

    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()
    private val featureManager: FeatureManager = object : FeatureManager {
        override val featureToggles: Map<ExperimentKey, FeatureToggle<*>> = emptyMap()
        override suspend fun suspendUntilLoaded() = Unit
    }
    private val tzProvider = { TimeZone.of("UTC+3") }
    private val savedStateHandle = SavedStateHandle()
    private val upcomingReleasesUseCase: TestUseCase = TestUseCase()
    private lateinit var viewModel: CalendarViewModel

    @BeforeEach
    fun setup() {
        viewModel = CalendarViewModel(
            featureManager = featureManager,
            logger = logger,
            tzProvider = tzProvider,
            savedStateHandle = savedStateHandle,
            getUpcomingReleasesByDateUseCase = upcomingReleasesUseCase,
        )
    }

    @Test
    fun `getViewState should return initial load on start`() = coroutinesExt.runTest {
        viewModel.viewState.value shouldBe InitialLoad
    }

    @Test
    fun `getViewState should return success state on success response`() = coroutinesExt.runTest {
        viewModel.viewState.test {
            advanceUntilIdle()
            awaitItem() shouldBe InitialLoad

            upcomingReleasesUseCase.flow.emit(
                NetworkRequestStatus.loading(),
            )
            expectNoEvents()

            upcomingReleasesUseCase.flow.emit(
                NetworkRequestStatus.completeSuccess(FAKE_SUCCESS_USE_CASE_RESPONSE),
            )
            advanceUntilIdle()

            awaitItem().let {
                it.shouldBeInstanceOf<CalendarScreenStateLoaded.Success>()
                it.isRefreshing shouldBe false
            }
        }
    }

    @Test
    fun `getViewState should return no internet on network failure`() = coroutinesExt.runTest {
        viewModel.viewState.test {
            advanceUntilIdle()
            awaitItem() shouldBe InitialLoad

            upcomingReleasesUseCase.flow.emit(
                NetworkRequestStatus.completeFailure(NetworkRequestFailure.NetworkFailure(NoRouteToHostException())),
            )

            awaitItem().let {
                it.shouldBeInstanceOf<CalendarScreenStateLoaded.Failure.NoInternet>()
                it.isRefreshing shouldBe false
            }
        }
    }

    @Test
    fun `getViewState should return network error on api failure`() = coroutinesExt.runTest {
        val useCase = TestUseCase()
        val vm = CalendarViewModel(
            featureManager = featureManager,
            logger = logger,
            tzProvider = tzProvider,
            savedStateHandle = savedStateHandle,
            getUpcomingReleasesByDateUseCase = useCase,
        )
        vm.viewState.test {
            advanceUntilIdle()
            awaitItem() shouldBe InitialLoad

            useCase.flow.emit(
                NetworkRequestStatus.completeFailure(
                    NetworkRequestFailure.NetworkFailure(
                        NoRouteToHostException(),
                    ),
                ),
            )

            awaitItem().let {
                it.shouldBeInstanceOf<CalendarScreenStateLoaded.Failure.NoInternet>()
                it.isRefreshing shouldBe false
            }
        }
    }

    class TestUseCase : ObserveUpcomingReleasesByDateUseCase {
        val flow: MutableSharedFlow<NetworkRequestStatus<UpcomingReleasesResponse>> = MutableSharedFlow()
        override fun createUpcomingReleasesObservable(
            requiredFields: Set<GameField>,
        ): Flow<NetworkRequestStatus<UpcomingReleasesResponse>> = flow
    }

    companion object {
        val FAKE_SUCCESS_USE_CASE_RESPONSE = UpcomingReleasesResponse(
            requestTime = Instant.parse("2024-01-01T01:00:00Z"),
            requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
            games = persistentListOf(),
        )
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.datetime.TimeZone
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.domain.upcoming.DefaultObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.model.InitialLoad
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers

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
    private lateinit var upcomingReleasesUseCase: TestUseCase
    private lateinit var viewModel: CalendarViewModel

    @BeforeEach
    fun setup() {
        upcomingReleasesUseCase = TestUseCase(coroutinesExt.testScope.backgroundScope)
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

    class TestUseCase(scope: CoroutineScope) : ObserveUpcomingReleasesByDateUseCase {
        var sourceList: MutableSharedFlow<List<UpcomingRelease>> = MutableSharedFlow()
        val pager = Pager(
            config = PagingConfig(pageSize = DefaultObserveUpcomingReleasesByDateUseCase.INITIAL_PAGING_SIZE),
            initialKey = null,
            pagingSourceFactory = sourceList.asPagingSourceFactory(scope),
        )

        override fun createUpcomingReleasesObservable(
            requiredFields: Set<GameField>,
        ): Flow<PagingData<UpcomingRelease>> = pager.flow
    }

    companion object {
        val FAKE_SUCCESS_USE_CASE_RESPONSE = PagingData.from(emptyList())
    }
}

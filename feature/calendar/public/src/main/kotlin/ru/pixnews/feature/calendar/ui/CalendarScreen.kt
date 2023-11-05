/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import ru.pixnews.feature.calendar.CalendarViewModel
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.converter.mergeScreenWithPagerStates
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded.Failure
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded.Success
import ru.pixnews.feature.calendar.model.CalendarScreenStateWithPagingState
import ru.pixnews.feature.calendar.model.InitialLoad
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.ui.failure.NoInternet
import ru.pixnews.feature.calendar.ui.failure.OtherNetworkError
import ru.pixnews.feature.calendar.ui.header.CalendarScreenHeader
import ru.pixnews.feature.calendar.ui.success.GameList
import ru.pixnews.foundation.di.ui.base.viewmodel.injectedViewModel
import ru.pixnews.foundation.ui.design.overlay.PixnewsLoadingOverlay
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.PreviewPhones

@Composable
internal fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = injectedViewModel(),
) {
    val upcomingReleases: LazyPagingItems<CalendarListItem> = viewModel.upcomingReleasesFlow.collectAsLazyPagingItems()
    CalendarScreen(
        modifier = modifier,
        calendarScreenState = viewModel.viewState,
        upcomingReleases = upcomingReleases,
        onRefreshRequested = { viewModel.refreshReleaseCalendarList() },
    )
}

@Composable
internal fun CalendarScreen(
    calendarScreenState: StateFlow<CalendarScreenState>,
    upcomingReleases: LazyPagingItems<CalendarListItem>,
    onRefreshRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    @SuppressLint("StateFlowValueCalledInComposition")
    val mergedState = remember(calendarScreenState, upcomingReleases) {
        val upcomingReleasesRefreshStatusFlow = snapshotFlow {
            upcomingReleases.loadState.refresh
        }
        calendarScreenState
            .combine(upcomingReleasesRefreshStatusFlow, ::CalendarScreenStateWithPagingState)
            .scan(CalendarScreenStateWithPagingState(), ::mergeScreenWithPagerStates)
            .map { it.screenState }
    }.collectAsStateWithLifecycle(initialValue = calendarScreenState.value)

    CalendarScreen(
        state = mergedState.value,
        upcomingReleases = upcomingReleases,
        onRefreshRequested = onRefreshRequested,
        modifier = modifier,
    )
}

@Composable
internal fun CalendarScreen(
    state: CalendarScreenState,
    upcomingReleases: LazyPagingItems<CalendarListItem>,
    onRefreshRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val testTag = when (state) {
            InitialLoad -> CalendarTestTag.INITIAL_LOADING_PLACEHOLDER
            is Failure.NoInternet -> CalendarTestTag.FAILURE_NO_INTERNET
            is Failure.OtherNetworkError -> CalendarTestTag.FAILURE_OTHER_NETWORK_ERROR
            is Success -> CalendarTestTag.SUCCESS_CONTENT
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag(testTag),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CalendarScreenHeader(
                onSearch = {},
                onDaySelectionClick = {},
                onYearMonthSelectionClick = {},
                onOpenFilterClick = {},
                onViewModeClick = {},
                onFilterChipClick = {},
            )

            when (state) {
                InitialLoad -> InitialLoadingPlaceholder()
                is Success -> GameList(
                    games = upcomingReleases,
                    majorReleases = state.majorReleases,
                    onMajorReleaseClick = {},
                    onGameClick = {},
                    onFavouriteClick = {},
                )

                is Failure.NoInternet -> NoInternet()
                is Failure.OtherNetworkError -> OtherNetworkError(
                    onRefreshClicked = onRefreshRequested,
                    refreshActive = state::isRefreshing,
                )
            }
        }

        AnimatedVisibility(
            visible = state is InitialLoad,
            enter = EnterTransition.None,
        ) {
            Box(
                modifier = Modifier
                    .testTag(CalendarTestTag.LOADING_OVERLAY),
            ) {
                PixnewsLoadingOverlay(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@PreviewPhones
@Composable
private fun CalendarScreenPreview() {
    PixnewsTheme {
        Surface {
            CalendarScreen(
                state = PreviewFixtures.previewSuccessState,
                upcomingReleases = flowOf(
                    PreviewFixtures.UpcomingReleases.successPagingData,
                ).collectAsLazyPagingItems(),
                onRefreshRequested = {},
            )
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreviewInitialLoadPlaceholder() {
    PixnewsTheme {
        Surface {
            CalendarScreen(
                state = InitialLoad,
                upcomingReleases = flowOf(PreviewFixtures.UpcomingReleases.initialLoadingPagingData)
                    .collectAsLazyPagingItems(),
                onRefreshRequested = {},
            )
        }
    }
}

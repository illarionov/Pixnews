/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import ru.pixnews.feature.calendar.converter.UpcomingGameListConverter.toCalendarListItem
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.model.CALENDAR_LIST_ITEM_GAME_FIELDS
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded
import ru.pixnews.feature.calendar.model.InitialLoad
import ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel
import ru.pixnews.foundation.featuretoggles.FeatureManager

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
@ContributesViewModel
internal class CalendarViewModel(
    featureManager: FeatureManager,
    // loadReleasesUseCase: LoadReleasesUseCase,
    getUpcomingReleasesByDateUseCase: ObserveUpcomingReleasesByDateUseCase,
    logger: Logger,
    tzProvider: Function0<@JvmSuppressWildcards TimeZone>,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val log = logger.withTag("CalendarViewModel")
    val viewState: StateFlow<CalendarScreenState> = flowOf(PreviewFixtures.previewSuccessState.majorReleases)
        .runningFold(InitialLoad as CalendarScreenState) { _, majorReleases ->
            CalendarScreenStateLoaded.Success(
                majorReleases = majorReleases,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000, replayExpirationMillis = 0),
            initialValue = InitialLoad,
        )
    val upcomingReleasesFlow: Flow<PagingData<CalendarListItem>> =
        getUpcomingReleasesByDateUseCase.createUpcomingReleasesObservable(
            requiredFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
        )
            .map { upcomingReleasesPagedData ->
                // TODO
                upcomingReleasesPagedData.map<UpcomingRelease, CalendarListItem> {
                    it.game.toCalendarListItem()
                }
            }
            .cachedIn(viewModelScope)

    fun refreshReleaseCalendarList() {
        TODO("Not yet implemented")
    }
}

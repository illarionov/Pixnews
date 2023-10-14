/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import ru.pixnews.feature.calendar.converter.UpcomingGameListConverter
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.feature.calendar.model.CALENDAR_LIST_ITEM_GAME_FIELDS
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.library.functional.RequestStatus
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.functional.RequestStatus.Loading
import ru.pixnews.library.functional.network.NetworkRequestFailure

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
    val viewState: StateFlow<CalendarScreenState> =
        getUpcomingReleasesByDateUseCase.createUpcomingReleasesObservable(
            requiredFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
        )
            .map { status: RequestStatus<NetworkRequestFailure<*>, UpcomingReleasesResponse> ->
                when (status) {
                    Loading -> CalendarScreenState.Loading
                    is Complete -> status.toCalendarState()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000, replayExpirationMillis = 0),
                initialValue = CalendarScreenState.Loading,
            )

    private fun Complete<*, UpcomingReleasesResponse>.toCalendarState(): CalendarScreenState {
        return this.result.fold(
            ifLeft = { failure ->
                // TODO:
                CalendarScreenState.Loading
            },
            ifRight = { response ->
                CalendarScreenState.Success(
                    majorReleases = PreviewFixtures.previewSuccessState.majorReleases,
                    games = UpcomingGameListConverter.convert(response),
                )
            },
        )
    }
}

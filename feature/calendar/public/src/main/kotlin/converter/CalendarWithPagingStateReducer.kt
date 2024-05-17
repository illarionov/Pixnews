/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded
import ru.pixnews.feature.calendar.model.CalendarScreenStateLoaded.Failure
import ru.pixnews.feature.calendar.model.CalendarScreenStateWithPagingState
import ru.pixnews.feature.calendar.model.InitialLoad
import ru.pixnews.library.functional.network.NetworkRequestFailure.NetworkFailure
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import java.net.SocketException
import java.net.UnknownHostException

@Suppress("UnusedParameter")
internal fun mergeScreenWithPagerStates(
    oldStateWithPagingState: CalendarScreenStateWithPagingState,
    newStateWithPagingState: CalendarScreenStateWithPagingState,
): CalendarScreenStateWithPagingState {
    val (screenState, upcomingReleasesRefreshState) = newStateWithPagingState

    val mergedScreenState = when (upcomingReleasesRefreshState) {
        is NotLoading -> screenState
        is Error -> upcomingReleasesRefreshState.error.toCalendarScreenStateFailure()
        Loading -> when (screenState) {
            InitialLoad -> InitialLoad
            is CalendarScreenStateLoaded -> {
                val isFirstReleasesLoad = oldStateWithPagingState.screenState is InitialLoad &&
                        upcomingReleasesRefreshState == Loading
                if (isFirstReleasesLoad) {
                    InitialLoad
                } else {
                    screenState.withIsRefreshing(true)
                }
            }
        }
    }

    return CalendarScreenStateWithPagingState(mergedScreenState, upcomingReleasesRefreshState)
}

private fun Throwable.toCalendarScreenStateFailure(): CalendarScreenStateLoaded.Failure = when {
    this is NetworkRequestFailureException -> when (failure) {
        is NetworkFailure -> Failure.NoInternet()
        else -> Failure.OtherNetworkError()
    }

    this.isNoInternetFailure() -> Failure.NoInternet()
    else -> Failure.OtherNetworkError(isRefreshing = false)
}

private fun Throwable.isNoInternetFailure(): Boolean =
    this is UnknownHostException ||
            this is SocketException

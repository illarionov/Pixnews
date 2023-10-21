/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import kotlinx.collections.immutable.ImmutableList

internal sealed interface CalendarScreenState

@Suppress("ConvertObjectToDataObject")
internal object InitialLoad : CalendarScreenState

internal sealed interface CalendarScreenStateLoaded : CalendarScreenState {
    val isRefreshing: Boolean
    fun withIsRefreshing(isRefreshing: Boolean): CalendarScreenState

    sealed interface Failure : CalendarScreenStateLoaded {
        data class NoInternet(
            override val isRefreshing: Boolean = false,
        ) : Failure {
            override fun withIsRefreshing(isRefreshing: Boolean): CalendarScreenState = copy(isRefreshing = isRefreshing)
        }

        data class OtherNetworkError(
            override val isRefreshing: Boolean = false,
        ) : Failure {
            override fun withIsRefreshing(isRefreshing: Boolean): CalendarScreenState = copy(isRefreshing = isRefreshing)
        }
    }

    data class Success(
        val majorReleases: ImmutableList<MajorReleaseCarouselItemUiModel>,
        val games: ImmutableList<CalendarListItem>,
        override val isRefreshing: Boolean = false,
        val showNoInternetError: Boolean = false,
    ) : CalendarScreenStateLoaded {
        override fun withIsRefreshing(isRefreshing: Boolean): CalendarScreenState = copy(isRefreshing = isRefreshing)
    }
}

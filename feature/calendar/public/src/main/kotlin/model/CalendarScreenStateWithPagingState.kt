/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import androidx.paging.LoadState

internal data class CalendarScreenStateWithPagingState(
    val screenState: CalendarScreenState = InitialLoad,
    val upcomingReleasesLoadingState: LoadState = LoadState.Loading,
)

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.pixnews.feature.calendar.ui.CalendarScreen
import ru.pixnews.foundation.di.ui.base.viewmodel.injectedViewModel

public const val CALENDAR_ROUTE: String = "calendar"

public fun NavController.navigateToCalendar(navOptions: NavOptions? = null) {
    this.navigate(CALENDAR_ROUTE, navOptions)
}

public fun NavGraphBuilder.calendarScreen() {
    composable(route = CALENDAR_ROUTE) {
        CalendarScreen(
            viewModel = injectedViewModel(),
        )
    }
}

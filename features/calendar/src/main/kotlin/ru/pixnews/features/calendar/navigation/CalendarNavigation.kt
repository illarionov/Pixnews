/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.features.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.pixnews.features.calendar.ui.CalendarScreen
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

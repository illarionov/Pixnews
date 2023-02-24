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
package ru.pixnews.features.root.model.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import ru.pixnews.foundation.ui.design.assets.R
import ru.pixnews.foundation.ui.design.assets.icons.navigation.NavigationIcons

@Immutable
public enum class TopLevelDestination(
    public val route: String,
    @StringRes public val title: Int,
    @StringRes public val contentDescription: Int,
    public val icon: NavigationIcons.IconPair,
) {
    CALENDAR(
        route = "calendar",
        title = R.string.bottom_bar_title_calendar,
        contentDescription = R.string.bottom_bar_content_description_calendar,
        icon = NavigationIcons.Calendar,
    ),
    COLLECTIONS(
        route = "collections",
        title = R.string.bottom_bar_title_collections,
        contentDescription = R.string.bottom_bar_content_description_collections,
        icon = NavigationIcons.Collections,
    ),
    PROFILE(
        route = "profile",
        title = R.string.bottom_bar_title_profile,
        contentDescription = R.string.bottom_bar_content_description_profile,
        icon = NavigationIcons.Profile,
    ),
    ;

    public companion object {
        public val START_DESTINATION: TopLevelDestination = CALENDAR
    }
}

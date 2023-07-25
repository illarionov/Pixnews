/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.root

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Immutable
import ru.pixnews.feature.calendar.navigation.CALENDAR_ROUTE
import ru.pixnews.feature.collections.navigation.COLLECTIONS_ROUTE
import ru.pixnews.feature.profile.navigation.PROFILE_ROUTE
import ru.pixnews.feature.root.TopLevelDestination.CALENDAR
import ru.pixnews.feature.root.TopLevelDestination.COLLECTIONS
import ru.pixnews.feature.root.TopLevelDestination.PROFILE
import ru.pixnews.feature.root.test.constants.BottomBarTestTags
import ru.pixnews.foundation.ui.assets.icons.navigation.NavigationIcons

@Immutable
public enum class TopLevelDestination(
    public val route: String,
    @get:VisibleForTesting @StringRes public val title: Int,
    @StringRes internal val contentDescription: Int,
    internal val icon: NavigationIcons.IconPair,
) {
    CALENDAR(
        route = CALENDAR_ROUTE,
        title = R.string.bottom_bar_title_calendar,
        contentDescription = R.string.bottom_bar_content_description_calendar,
        icon = NavigationIcons.Calendar,
    ),
    COLLECTIONS(
        route = COLLECTIONS_ROUTE,
        title = R.string.bottom_bar_title_collections,
        contentDescription = R.string.bottom_bar_content_description_collections,
        icon = NavigationIcons.Collections,
    ),
    PROFILE(
        route = PROFILE_ROUTE,
        title = R.string.bottom_bar_title_profile,
        contentDescription = R.string.bottom_bar_content_description_profile,
        icon = NavigationIcons.Profile,
    ),
    ;

    public companion object {
        public val START_DESTINATION: TopLevelDestination = CALENDAR
    }
}

internal val TopLevelDestination.testTag: String
    get() = when (this) {
        CALENDAR -> BottomBarTestTags.CALENDAR
        COLLECTIONS -> BottomBarTestTags.COLLECTIONS
        PROFILE -> BottomBarTestTags.PROFILE
    }

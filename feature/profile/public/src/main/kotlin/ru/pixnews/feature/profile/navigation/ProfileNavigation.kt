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
package ru.pixnews.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.pixnews.feature.profile.ProfileScreen

public const val PROFILE_ROUTE: String = "profile"

public fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_ROUTE, navOptions)
}

public fun NavGraphBuilder.profileScreen() {
    composable(route = PROFILE_ROUTE) {
        ProfileScreen()
    }
}

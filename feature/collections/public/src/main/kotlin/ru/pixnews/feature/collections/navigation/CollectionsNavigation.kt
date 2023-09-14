/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.collections.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.pixnews.feature.collections.CollectionsScreen

public const val COLLECTIONS_ROUTE: String = "collections"

public fun NavController.navigateToCollections(navOptions: NavOptions? = null) {
    this.navigate(COLLECTIONS_ROUTE, navOptions)
}

public fun NavGraphBuilder.collectionsScreen() {
    composable(route = COLLECTIONS_ROUTE) {
        CollectionsScreen()
    }
}

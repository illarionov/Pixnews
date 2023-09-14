/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons
import ru.pixnews.foundation.ui.design.R as uiDesignR

public enum class GameListViewMode(
    public val icon: ImageVector,
    @StringRes public val contentDescription: Int,
) {
    LIST(
        icon = ContentIcons.GridView.Unfilled,
        contentDescription = uiDesignR.string.switch_display_mode_to_grid_content_description,
    ),
    GRID(
        icon = ContentIcons.GridView.Filled,
        contentDescription = uiDesignR.string.switch_display_mode_to_list_content_description,
    ),
    ;

    public fun next(): GameListViewMode = when (this) {
        LIST -> GRID
        GRID -> LIST
    }
}

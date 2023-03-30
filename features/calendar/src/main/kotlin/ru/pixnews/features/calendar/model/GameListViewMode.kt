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
package ru.pixnews.features.calendar.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons
import ru.pixnews.foundation.ui.design.R as uiDesignR

internal enum class GameListViewMode(
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
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

    fun next() = when (this) {
        LIST -> GRID
        GRID -> LIST
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.SELECTED

public data class GameListFilterChip(
    val id: String,
    val label: String,
    val style: GameListFilterChipStyle = SELECTED,
)

public enum class GameListFilterChipStyle {
    SELECTED,
    UNSELECTED,
}

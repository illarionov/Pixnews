/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.game.GameReleaseStatus
import ru.pixnews.domain.model.game.GameReleaseStatus.ALPHA
import ru.pixnews.domain.model.game.GameReleaseStatus.BETA
import ru.pixnews.domain.model.game.GameReleaseStatus.CANCELLED
import ru.pixnews.domain.model.game.GameReleaseStatus.DELISTED
import ru.pixnews.domain.model.game.GameReleaseStatus.EARLY_ACCESS
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameReleaseStatus.OFFLINE
import ru.pixnews.domain.model.game.GameReleaseStatus.RELEASED
import ru.pixnews.domain.model.game.GameReleaseStatus.RUMORED
import ru.pixnews.foundation.database.util.errorUnknownValue

@Suppress("MagicNumber")
internal object GameReleaseStatusTypeConverter {
    @TypeConverter
    fun fromGameReleaseStatus(status: GameReleaseStatus?): Int? = when (status) {
        NOT_YET_RELEASED -> 0
        RELEASED -> 1
        ALPHA -> 2
        BETA -> 3
        EARLY_ACCESS -> 4
        OFFLINE -> 5
        CANCELLED -> 6
        RUMORED -> 7
        DELISTED -> 8
        null -> null
    }

    @TypeConverter
    fun toGameReleaseStatus(value: Int?): GameReleaseStatus? = when (value) {
        null -> null
        0 -> NOT_YET_RELEASED
        1 -> RELEASED
        2 -> ALPHA
        3 -> BETA
        4 -> EARLY_ACCESS
        5 -> OFFLINE
        6 -> CANCELLED
        7 -> RUMORED
        8 -> DELISTED
        else -> errorUnknownValue(value, GameReleaseStatus::class)
    }
}

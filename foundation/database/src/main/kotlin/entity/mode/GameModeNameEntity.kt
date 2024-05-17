/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.mode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

@Entity(
    tableName = "gameModeName",
    foreignKeys = [
        ForeignKey(
            entity = GameModeEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameModeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("gameModeId", "languageCode", unique = true),
    ],
)
public data class GameModeNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameModeId: Long,
    val languageCode: LanguageCodeWrapper = LanguageCodeWrapper(LanguageCode.ENGLISH),
    @ColumnInfo(
        collate = ColumnInfo.UNICODE,
        index = true,
    )
    val name: String,
)

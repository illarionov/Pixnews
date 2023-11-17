/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.perspective

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

@Entity(
    tableName = "playerPerspectiveName",
    foreignKeys = [
        ForeignKey(
            entity = PlayerPerspectiveEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerPerspectiveId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("playerPerspectiveId", "languageCode", unique = true),
    ],
)
public data class PlayerPerspectiveNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val playerPerspectiveId: Long,
    val languageCode: LanguageCodeWrapper = LanguageCodeWrapper(LanguageCode.ENGLISH),
    @ColumnInfo(collate = ColumnInfo.UNICODE, index = true)
    val name: String,
)

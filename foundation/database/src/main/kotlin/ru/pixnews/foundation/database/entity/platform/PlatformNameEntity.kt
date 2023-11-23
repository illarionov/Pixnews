/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.platform

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

@Entity(
    tableName = "platformName",
    foreignKeys = [
        ForeignKey(
            entity = PlatformEntity::class,
            parentColumns = ["id"],
            childColumns = ["gamePlatformId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("gamePlatformId", "languageCode", unique = true),
    ],
)
public data class PlatformNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gamePlatformId: Long,
    val languageCode: LanguageCodeWrapper = LanguageCodeWrapper(LanguageCode.ENGLISH),
    @ColumnInfo(
        /* collate = ColumnInfo.UNICODE, TODO: unsupported in prefiller */
        index = true,
    )
    val name: String,
)

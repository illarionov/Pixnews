/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.genre

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.model.LanguageCodeWrapper

/**
 * Localized name of the [GenreEntity]
 */
@Entity(
    tableName = "genreName",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("genreId"),
    ],
)
public data class GenreNameEntity(
    @PrimaryKey(autoGenerate = true)
    public val id: Long = 0,
    val genreId: Long,
    val languageCode: LanguageCodeWrapper,

    /* @ColumnInfo(collate = ColumnInfo.UNICODE) TODO: unsupported in prefiller */
    val name: String,
)

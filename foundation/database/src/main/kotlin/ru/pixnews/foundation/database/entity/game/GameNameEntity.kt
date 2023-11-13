/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.game

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.domain.model.datasource.DataSourceType
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

@Entity(
    tableName = "gameName",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("gameId"),
        Index("dataSourceId"),
    ],
)
public data class GameNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val isPrimary: Boolean = true,
    val languageCode: LanguageCodeWrapper = LanguageCodeWrapper(LanguageCode.ENGLISH),
    val dataSourceId: DataSourceType,
    val text: String,
)

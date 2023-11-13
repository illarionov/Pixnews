/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.game

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.domain.model.game.GameReleaseStatus
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.foundation.database.entity.embedded.AgeRankingEntity
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity

@Entity(
    tableName = "game",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentGameId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["releaseCategory_parentGameId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("parentGameId"),
        Index("releaseCategory_parentGameId"),
        Index("releaseDate_type", "releaseDate_timestamp"),
    ],
)
public data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @Embedded(prefix = "releaseDate_")
    val releaseDate: GameReleaseDateEntity,
    val releaseStatus: GameReleaseStatus = NOT_YET_RELEASED,

    @Embedded(prefix = "releaseCategory_")
    val gameReleaseCategory: GameReleaseCategoryEntity?,
    val parentGameId: Long? = null,

    @Embedded("ageRanking_")
    val ageRanking: AgeRankingEntity?,
)

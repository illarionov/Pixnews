/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.entity.game.GameEntity
import ru.pixnews.foundation.database.entity.perspective.PlayerPerspectiveEntity

@Entity(
    tableName = "gamePlayerPerspective",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
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
        Index("gameId", "playerPerspectiveId", unique = true),
        Index("playerPerspectiveId"),
    ],
)
public data class GamePlayerPerspectiveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val playerPerspectiveId: Long,
)

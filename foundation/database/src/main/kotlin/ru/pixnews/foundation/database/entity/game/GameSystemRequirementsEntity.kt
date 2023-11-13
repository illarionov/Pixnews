/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.game

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.entity.game.GameSystemRequirementsEntity.Type.MINIMUM
import ru.pixnews.foundation.database.entity.platform.PlatformEntity

@Entity(
    tableName = "gameSystemRequirements",
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
            entity = PlatformEntity::class,
            parentColumns = ["id"],
            childColumns = ["gamePlatformId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("gameId", "gamePlatformId", "type", unique = true),
        Index("gamePlatformId"),
    ],
)
public data class GameSystemRequirementsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val gamePlatformId: Long,
    val type: Type = MINIMUM,
    val os: String = "",
    val cpu: String = "",
    val ram: String = "",
    val video: String = "",
    val disk: String = "",
    val other: String = "",
) {
    public enum class Type {
        MINIMUM, RECOMMENDED
    }
}

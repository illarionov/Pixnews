/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.entity.company.CompanyEntity
import ru.pixnews.foundation.database.entity.game.GameEntity

@Entity(
    tableName = "gameCompany",
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
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("gameId", "companyId", "type", unique = true),
        Index("companyId"),
    ],
)
public data class GameCompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val companyId: Long,
    val type: Type,
) {
    public enum class Type(internal val databaseId: Int) {
        DEVELOPER(1),
        PUBLISHER(2),
    }
}

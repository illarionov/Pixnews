/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import ru.pixnews.domain.model.util.RefType
import ru.pixnews.domain.model.util.RefType.FULL

@Suppress("WRONG_ORDER_IN_CLASS_LIKE_STRUCTURES")
public sealed class GameField {
    public data object Id : GameField()
    public data object Name : GameField()
    public data object Summary : GameField()
    public data object Description : GameField()
    public data object VideoUrls : GameField()
    public data object Screenshots : GameField()
    public data object Developer : GameField()
    public data object Publisher : GameField()
    public data object ReleaseDate : GameField()
    public data object ReleaseStatus : GameField()
    public data object Genres : GameField()
    public data object Tags : GameField()
    public data object Ratings : GameField()
    public data object Links : GameField()
    public data object Category : GameField()
    public data class ParentGame(val type: RefType = FULL) : GameField()
    public data class Series(val type: RefType = FULL) : GameField()
    public data class Platforms(val type: RefType = FULL) : GameField()
    public data object AgeRanking : GameField()
    public data object Localizations : GameField()
    public data class GameMode(val type: RefType = FULL) : GameField()
    public data class PlayerPerspective(val type: RefType = FULL) : GameField()
    public data object SystemRequirements : GameField()
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.dsl.field.field
import at.released.igdbclient.model.Game as IgdbGame

internal interface IgdbGameFieldConverter<out O : Any?> {
    fun getRequiredFields(from: GameFieldDsl = IgdbGame.field): List<IgdbRequestField<*>>
    fun convert(game: IgdbGame): O
}

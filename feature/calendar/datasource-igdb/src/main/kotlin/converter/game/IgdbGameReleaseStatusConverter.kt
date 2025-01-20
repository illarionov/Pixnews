/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.GameStatusEnum
import at.released.igdbclient.model.GameStatusEnum.ALPHA
import at.released.igdbclient.model.GameStatusEnum.BETA
import at.released.igdbclient.model.GameStatusEnum.CANCELLED
import at.released.igdbclient.model.GameStatusEnum.DELISTED
import at.released.igdbclient.model.GameStatusEnum.EARLY_ACCESS
import at.released.igdbclient.model.GameStatusEnum.OFFLINE
import at.released.igdbclient.model.GameStatusEnum.RELEASED
import at.released.igdbclient.model.GameStatusEnum.RUMORED
import ru.pixnews.domain.model.game.GameReleaseStatus
import at.released.igdbclient.model.Game as IgdbGame

internal object IgdbGameReleaseStatusConverter : IgdbGameFieldConverter<GameReleaseStatus?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(from.status)

    override fun convert(game: IgdbGame): GameReleaseStatus = convertReleaseStatus(game.status)

    private fun convertReleaseStatus(status: GameStatusEnum): GameReleaseStatus = when (status) {
        RELEASED -> GameReleaseStatus.RELEASED
        ALPHA -> GameReleaseStatus.ALPHA
        BETA -> GameReleaseStatus.BETA
        EARLY_ACCESS -> GameReleaseStatus.EARLY_ACCESS
        OFFLINE -> GameReleaseStatus.OFFLINE
        CANCELLED -> GameReleaseStatus.CANCELLED
        RUMORED -> GameReleaseStatus.RUMORED
        DELISTED -> GameReleaseStatus.DELISTED
    }
}

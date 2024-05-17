/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import ru.pixnews.domain.model.game.GameReleaseStatus
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.GameStatusEnum
import ru.pixnews.igdbclient.model.GameStatusEnum.ALPHA
import ru.pixnews.igdbclient.model.GameStatusEnum.BETA
import ru.pixnews.igdbclient.model.GameStatusEnum.CANCELLED
import ru.pixnews.igdbclient.model.GameStatusEnum.DELISTED
import ru.pixnews.igdbclient.model.GameStatusEnum.EARLY_ACCESS
import ru.pixnews.igdbclient.model.GameStatusEnum.OFFLINE
import ru.pixnews.igdbclient.model.GameStatusEnum.RELEASED
import ru.pixnews.igdbclient.model.GameStatusEnum.RUMORED
import ru.pixnews.igdbclient.model.Game as IgdbGame

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

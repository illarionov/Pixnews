/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import at.released.igdbclient.model.GameVideo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.url.VideoUrl
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.url.IgdbVideoUrl

internal object IgdbGameVideoUrlsConverter : IgdbGameFieldConverter<ImmutableList<VideoUrl>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(from.videos.video_id)

    override fun convert(game: Game): ImmutableList<VideoUrl> = game.videos
        .map(::convertGameVideoToVideoUrl)
        .toImmutableList()

    fun convertGameVideoToVideoUrl(gameVideo: GameVideo): VideoUrl = IgdbVideoUrl(
        igdbVideoId = requireFieldInitialized("videos.video_id", gameVideo.video_id),
    )
}

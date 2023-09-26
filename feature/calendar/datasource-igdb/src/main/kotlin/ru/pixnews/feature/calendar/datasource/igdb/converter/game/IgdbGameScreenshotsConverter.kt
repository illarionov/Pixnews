/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.url.IgdbImageUrl
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Cover
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Screenshot

internal object IgdbGameScreenshotsConverter : IgdbGameFieldConverter<ImmutableList<ImageUrl>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.cover.image_id,
        from.cover.animated,
        from.cover.width,
        from.cover.height,
        from.screenshots.image_id,
        from.screenshots.animated,
        from.screenshots.width,
        from.screenshots.height,
    )

    override fun convert(game: Game): ImmutableList<ImageUrl> {
        val cover = game.cover?.let {
            sequenceOf(convertCoverToImageUrl(it))
        } ?: emptySequence()

        val screenshots = game.screenshots
            .asSequence()
            .map(::convertScreenshotToImageUrl)

        return (cover + screenshots).toImmutableList()
    }

    fun convertCoverToImageUrl(cover: Cover): ImageUrl = with(cover) {
        IgdbImageUrl(
            igdbImageId = requireFieldInitialized("cover.image_id", image_id),
            animated = animated,
            size = if (width != 0 && height != 0) {
                CanvasSize(width.toUInt(), height.toUInt())
            } else {
                null
            },
        )
    }

    fun convertScreenshotToImageUrl(screenshot: Screenshot): ImageUrl = with(screenshot) {
        IgdbImageUrl(
            igdbImageId = requireFieldInitialized("screenshots.image_id", image_id),
            animated = this.animated,
            size = if (this.width != 0 && this.height != 0) {
                CanvasSize(this.width.toUInt(), this.height.toUInt())
            } else {
                null
            },
        )
    }
}

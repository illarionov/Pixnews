/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.model

import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardGridSmallUiModel

internal data class MajorReleaseCarouselItemUiModel(
    override val gameId: GameId,
    override val title: String,
    override val cover: ImageUrl?,
    override val platforms: ImmutableSet<GamePlatform>,
    override val favourite: Boolean,
) : PixnewsGameCardGridSmallUiModel

internal fun Game.toMajorReleaseCarouselItemUiModel(): MajorReleaseCarouselItemUiModel {
    return MajorReleaseCarouselItemUiModel(
        gameId = id,
        title = name.value,
        cover = screenshots.firstOrNull(),
        platforms = platforms,
        favourite = false,
    )
}

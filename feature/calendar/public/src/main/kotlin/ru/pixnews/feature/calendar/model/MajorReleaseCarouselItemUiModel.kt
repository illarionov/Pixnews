/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.getObjectOrThrow
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
        platforms = platforms.map(Ref<GamePlatform, *>::getObjectOrThrow).toImmutableSet(),
        favourite = false,
    )
}

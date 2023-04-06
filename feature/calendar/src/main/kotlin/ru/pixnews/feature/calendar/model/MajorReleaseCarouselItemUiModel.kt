/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

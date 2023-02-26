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
package ru.pixnews.foundation.ui.design.card

import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameGenre

internal fun Game.toGameCardItem(favourite: Boolean = false): PixnewsGameCardUiModel {
    return object : PixnewsGameCardUiModel {
        override val gameId = id
        override val title = name.value
        override val description = summary.value.asPlainText()
        override val cover = screenshots.firstOrNull()
        override val platforms = this@toGameCardItem.platforms
        override val favourite = favourite
        override val genres = this@toGameCardItem.genres.map(GameGenre::name).joinToString()
    }
}

internal fun Game.toGameCardGridMajorReleasesUiItem(favourite: Boolean = false): PixnewsGameCardGridSmallUiModel {
    return object : PixnewsGameCardGridSmallUiModel {
        override val gameId = id
        override val title = name.value
        override val cover = screenshots.firstOrNull()
        override val platforms = this@toGameCardGridMajorReleasesUiItem.platforms
        override val favourite = favourite
    }
}

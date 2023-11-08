/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.card

import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.getObjectOrThrow

public fun Game.toGameCardItem(favourite: Boolean = false): PixnewsGameCardUiModel {
    return object : PixnewsGameCardUiModel {
        override val gameId = id
        override val title = name.value
        override val description = summary.value.asPlainText()
        override val cover = screenshots.firstOrNull()
        override val platforms = this@toGameCardItem.platforms
            .map(Ref<GamePlatform>::getObjectOrThrow)
            .toImmutableSet()
        override val favourite = favourite
        override val genres = this@toGameCardItem.genres.map(GameGenre::name).joinToString()
        override val releaseDate: UpcomingReleaseDateUiModel = this@toGameCardItem.releaseDate.toUiModel()
    }
}

public fun Game.toGameCardGridMajorReleasesUiItem(favourite: Boolean = false): PixnewsGameCardGridSmallUiModel {
    return object : PixnewsGameCardGridSmallUiModel {
        override val gameId = id
        override val title = name.value
        override val cover = screenshots.firstOrNull()
        override val platforms = this@toGameCardGridMajorReleasesUiItem.platforms
            .map(Ref<GamePlatform>::getObjectOrThrow)
            .toImmutableSet()
        override val favourite = favourite
    }
}

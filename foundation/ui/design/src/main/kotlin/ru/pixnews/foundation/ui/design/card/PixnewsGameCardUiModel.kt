/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.card

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.ImageUrl

@Immutable
public interface PixnewsGameCardUiModel {
    public val gameId: GameId
    public val title: String
    public val description: String
    public val cover: ImageUrl?
    public val platforms: ImmutableSet<GamePlatform>
    public val favourite: Boolean
    public val genres: String
    public val releaseDate: Date
}

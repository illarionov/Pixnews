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
package ru.pixnews.features.calendar.model

import android.os.Parcelable
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.parcelize.Parcelize
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.features.calendar.model.CalendarListItemContentType.GAME
import ru.pixnews.features.calendar.model.CalendarListItemContentType.TITLE
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardUiModel

internal sealed interface CalendarListItem {
    val uniqueId: CalendarListItemId
}

internal data class CalendarListTitle(
    val title: String,
) : CalendarListItem {
    override val uniqueId = CalendarListItemId(TITLE, title)
}

internal data class CalendarListPixnewsGameUi(
    override val gameId: GameId,
    override val title: String,
    override val description: String,
    override val cover: ImageUrl?,
    override val platforms: ImmutableSet<GamePlatform>,
    override val favourite: Boolean,
    override val genres: String,
) : PixnewsGameCardUiModel, CalendarListItem {
    override val uniqueId = CalendarListItemId(GAME, gameId.toString())
}

internal enum class CalendarListItemContentType {
    TITLE,
    GAME,
    ;
}

@Parcelize
internal data class CalendarListItemId(
    val contentType: CalendarListItemContentType,
    val id: String,
) : Parcelable

internal fun Game.toCalendarListItem(): CalendarListPixnewsGameUi {
    return CalendarListPixnewsGameUi(
        gameId = id,
        title = name.value,
        description = summary.value.asPlainText(),
        cover = screenshots.firstOrNull(),
        platforms = platforms,
        favourite = false,
        genres = genres.map(GameGenre::name).joinToString(),
    )
}

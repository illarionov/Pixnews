/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import android.os.Parcelable
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.parcelize.Parcelize
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.getObjectOrThrow
import ru.pixnews.feature.calendar.model.CalendarListItemContentType.GAME
import ru.pixnews.feature.calendar.model.CalendarListItemContentType.TITLE
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
}

@Parcelize
internal data class CalendarListItemId(
    val contentType: CalendarListItemContentType,
    val id: String,
) : Parcelable

internal val CALENDAR_LIST_ITEM_GAME_FIELDS = setOf(
    GameField.Id,
    GameField.Name,
    GameField.Summary,
    GameField.Screenshots,
    GameField.Platforms(),
    GameField.Genres,
)

internal fun Game.toCalendarListItem(): CalendarListPixnewsGameUi {
    return CalendarListPixnewsGameUi(
        gameId = id,
        title = name.value,
        description = summary.value.asPlainText(),
        cover = screenshots.firstOrNull(),
        platforms = platforms.map(Ref<GamePlatform>::getObjectOrThrow).toImmutableSet(),
        favourite = false,
        genres = genres.map(GameGenre::name).joinToString(),
    )
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.model

import android.os.Parcelable
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.parcelize.Parcelize
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.feature.calendar.model.CalendarListItemContentType.GAME
import ru.pixnews.feature.calendar.model.CalendarListItemContentType.TITLE
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.test.constants.toGroupId
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardUiModel

internal sealed interface CalendarListItem {
    val uniqueId: CalendarListItemId
}

internal data class CalendarListTitle(
    val groupId: Date,
) : CalendarListItem {
    override val uniqueId: CalendarListItemId.Title = CalendarListItemId.Title(groupId)
}

internal data class CalendarListPixnewsGameUi(
    override val gameId: GameId,
    override val title: String,
    override val description: String,
    override val cover: ImageUrl?,
    override val platforms: ImmutableSet<GamePlatform>,
    override val favourite: Boolean,
    override val genres: String,
    override val releaseDate: Date,
) : PixnewsGameCardUiModel, CalendarListItem {
    override val uniqueId = CalendarListItemId.GameId(gameId.toString())
}

internal enum class CalendarListItemContentType {
    TITLE,
    GAME,
}

@Parcelize
internal sealed class CalendarListItemId(
    val contentType: CalendarListItemContentType,
) : Parcelable {
    internal data class GameId(
        val gameId: String,
    ) : CalendarListItemId(GAME)

    internal data class Title(
        val groupId: UpcomingReleaseGroupId,
    ) : CalendarListItemId(TITLE) {
        internal constructor(date: Date) : this(date.toGroupId())
    }
}

internal val CALENDAR_LIST_ITEM_GAME_FIELDS = persistentSetOf(
    GameField.Id,
    GameField.Name,
    GameField.ReleaseDate,
    GameField.Summary,
    GameField.Screenshots,
    GameField.Platforms(),
    GameField.Genres,
)

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.domain.upcoming

import androidx.paging.PagingSource
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.domain.upcoming.IgdbPagingSource.IgdbPagingSourceKey

public abstract class IgdbPagingSource : PagingSource<IgdbPagingSourceKey, Game>() {
    @JvmInline
    public value class IgdbPagingSourceKey(
        public val offset: Int,
    )

    public fun interface Factory {
        public fun create(
            startDate: Instant,
            requiredFields: Set<GameField>,
        ): IgdbPagingSource
    }
}

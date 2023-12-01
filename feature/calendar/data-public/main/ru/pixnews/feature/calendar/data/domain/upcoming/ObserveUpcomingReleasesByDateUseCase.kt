/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.domain.upcoming

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField

public interface ObserveUpcomingReleasesByDateUseCase {
    public fun createUpcomingReleasesObservable(requiredFields: Set<GameField>): Flow<PagingData<UpcomingRelease>>

    public data class UpcomingRelease(
        val game: Game,
        val group: UpcomingReleaseTimeCategory,
    )
}

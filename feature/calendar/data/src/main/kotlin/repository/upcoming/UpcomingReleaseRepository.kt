/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.repository.upcoming

import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.GameField

public interface UpcomingReleaseRepository {
    public fun createUpcomingReleasesPagingSource(
        startDate: Instant,
        requiredFields: Set<GameField>,
    ): UpcomingReleasesPagingSource
}

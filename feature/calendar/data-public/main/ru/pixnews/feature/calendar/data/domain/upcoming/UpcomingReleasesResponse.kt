/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.domain.upcoming

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.GameField

public data class UpcomingReleasesResponse(
    val requestTime: Instant,
    val requestedFields: ImmutableSet<GameField>,
    val games: ImmutableList<UpcomingRelease>,
) {
    public companion object
}
/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.domain.upcoming

import kotlinx.coroutines.flow.Flow
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.library.functional.network.NetworkRequestStatus

public interface ObserveUpcomingReleasesByDateUseCase {
    public fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<UpcomingReleasesResponse>>
}

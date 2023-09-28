/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.data

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.beyondGoodEvil2
import ru.pixnews.domain.model.game.game.gta6
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.project007
import ru.pixnews.domain.model.game.game.sims5
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.domain.model.game.game.smalland
import ru.pixnews.domain.model.game.game.starWarsEclipse
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.feature.calendar.data.domain.ObserveUpcomingReleasesByDateUseCase
import ru.pixnews.feature.calendar.domain.DefaultObserveUpcomingReleasesByDateUseCase
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.network.NetworkRequestStatus
import javax.inject.Inject

@ContributesBinding(
    boundType = ObserveUpcomingReleasesByDateUseCase::class,
    scope = AppScope::class,
    replaces = [DefaultObserveUpcomingReleasesByDateUseCase::class],
)
class MockObserveUpcomingReleasesByDateUseCase @Inject constructor() : ObserveUpcomingReleasesByDateUseCase {
    override fun createUpcomingReleasesObservable(
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<List<Game>>> {
        return flowOf(
            NetworkRequestStatus.completeSuccess(
                listOf(
                    GameFixtures.slimeRancher2,
                    GameFixtures.hytale,
                    GameFixtures.gta6,
                    GameFixtures.theLostWild,
                    GameFixtures.sims5,
                    GameFixtures.beyondGoodEvil2,
                    GameFixtures.starWarsEclipse,
                    GameFixtures.halfLife3,
                    GameFixtures.smalland,
                    GameFixtures.project007,
                ),
            ),
        )
    }
}

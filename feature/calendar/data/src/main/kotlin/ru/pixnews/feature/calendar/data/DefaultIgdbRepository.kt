/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.data

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.Game.Companion.GameField
import ru.pixnews.feature.calendar.domain.DefaultObserveUpcomingReleasesByDateUseCase.Companion.IgdbRepository
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.network.NetworkRequestStatus
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class DefaultIgdbRepository @Inject constructor(
    private val igdbDataSource: IgdbDataSource,
) : IgdbRepository {
    override fun createUpcomingReleasesObservable(
        startDate: Instant,
        requiredFields: Set<GameField>,
    ): Flow<NetworkRequestStatus<List<Game>>> {
        return flow {
            emit(NetworkRequestStatus.loading())
            val result = igdbDataSource.fetchUpcomingReleases(
                startDate = startDate,
                requiredFields = requiredFields,
            )
                .fold(
                    ifLeft = {
                        NetworkRequestStatus.completeFailure(it)
                    },
                    ifRight = {
                        NetworkRequestStatus.completeSuccess(it)
                    },
                )
            emit(result)
        }
    }
}

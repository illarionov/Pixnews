/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data

import androidx.paging.PagingState
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.domain.upcoming.IgdbPagingSource
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class DefaultUpcomingReleasePagingSourceFactory @Inject constructor(
    private val igdbDataSource: IgdbDataSource,
) : IgdbPagingSource.Factory {
    override fun create(
        startDate: Instant,
        requiredFields: Set<GameField>,
    ): IgdbPagingSource = UpcomingReleasePagingSource(startDate, requiredFields, igdbDataSource)

    private class UpcomingReleasePagingSource(
        private val startDate: Instant,
        private val requiredFields: Set<GameField>,
        private val igdbDataSource: IgdbDataSource,
    ) : IgdbPagingSource() {
        override fun getRefreshKey(
            state: PagingState<IgdbPagingSourceKey, Game>,
        ): IgdbPagingSourceKey? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                    ?: return@let null
                when {
                    anchorPage.prevKey != null -> IgdbPagingSourceKey(
                        anchorPage.prevKey!!.offset + anchorPage.data.size,
                    )

                    anchorPage.nextKey != null -> IgdbPagingSourceKey(
                        anchorPage.nextKey!!.offset - anchorPage.data.size,
                    )

                    else -> null
                }
            }
        }

        override suspend fun load(
            params: LoadParams<IgdbPagingSourceKey>,
        ): LoadResult<IgdbPagingSourceKey, Game> {
            val offset = params.key?.offset ?: 0
            val limit = params.loadSize
            return igdbDataSource.fetchUpcomingReleases(
                startDate = startDate,
                requiredFields = requiredFields,
                offset = offset,
                limit = limit,
            ).fold(
                ifLeft = {
                    LoadResult.Error(NetworkRequestFailureException(it))
                },
                ifRight = { games ->
                    LoadResult.Page(
                        data = games,
                        prevKey = null,
                        nextKey = if (games.isNotEmpty()) {
                            IgdbPagingSourceKey(offset + games.size)
                        } else {
                            null
                        },
                    )
                },
            )
        }
    }
}
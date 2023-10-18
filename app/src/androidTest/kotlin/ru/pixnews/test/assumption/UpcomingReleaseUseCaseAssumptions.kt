/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.assumption

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.inject.MockResourcesHolder
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase.UpcomingReleasesDateFixtures.NOT_INITIALIZED
import ru.pixnews.library.functional.network.NetworkRequestStatus

class UpcomingReleaseUseCaseAssumptions : ExternalResource() {
    private lateinit var mockUseCase: MockObserveUpcomingReleasesByDateUseCase

    override fun apply(base: Statement?, description: Description?): Statement {
        mockUseCase = (PixnewsRootComponentHolder.appComponent as MockResourcesHolder).mockUseCase
        return super.apply(base, description)
    }

    override fun before() {
        super.before()
        assumeUpcomingGamesResponseDefaultGame()
    }

    override fun after() {
        super.after()
        resetMockUseCase()
    }

    fun assumeUpcomingGamesResponseDefaultGame() {
        mockUseCase.createUpcomingReleasesObservableResponse = { requiredFields ->
            flowOf(
                NetworkRequestStatus.completeSuccess(
                    UpcomingReleasesResponse(
                        requestTime = DEFAULT_TIME.toInstant(DEFAULT_TIME_ZONE),
                        requestedFields = requiredFields.toImmutableSet(),
                        games = persistentListOf(DEFAULT_UPCOMING_RELEASE),
                    ),
                ),
            )
        }
    }

    fun resetMockUseCase() {
        mockUseCase.createUpcomingReleasesObservableResponse = NOT_INITIALIZED
    }

    fun assumeUpcomingGamesResponseEmpty() {
        mockUseCase.createUpcomingReleasesObservableResponse = { requiredFields ->
            flowOf(
                NetworkRequestStatus.completeSuccess(
                    UpcomingReleasesResponse(
                        requestTime = Clock.System.now(),
                        requestedFields = requiredFields.toImmutableSet(),
                        games = persistentListOf(),
                    ),
                ),
            )
        }
    }

    fun assumeUpcomingReleasesSuccessfully(
        releases: List<UpcomingRelease>,
        requestTime: Instant = Clock.System.now(),
    ) {
        mockUseCase.createUpcomingReleasesObservableResponse = { requiredFields ->
            flowOf(
                NetworkRequestStatus.completeSuccess(
                    UpcomingReleasesResponse(
                        requestTime = requestTime,
                        requestedFields = requiredFields.toImmutableSet(),
                        games = releases.toPersistentList(),
                    ),
                ),
            )
        }
    }

    public companion object {
        val DEFAULT_TIME = LocalDateTime(2023, 5, 17, 10, 0, 0, 0)
        val DEFAULT_TIME_ZONE = TimeZone.of("UTC+3")
        val DEFAULT_UPCOMING_RELEASE = UpcomingRelease(
            // TODO: loading with debug
            game = GameFixtures.slimeRancher2.copy(
                releaseDate = Date.YearMonthDay(2023, Month.MAY, 17),
            ),
            group = FEW_DAYS,
        )
    }
}

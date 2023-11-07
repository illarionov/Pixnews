/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performScrollToNode
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.test.element.GameFeedElement
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.di.ContributesTest
import ru.pixnews.foundation.instrumented.test.di.rule.InjectDependenciesRule
import ru.pixnews.test.assumption.UpcomingReleaseUseCaseAssumptions

@ContributesTest
class CalendarScreenGameListTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val gameFeed = GameFeedElement(composeTestRule)

    @get:Rule
    val upcomingReleaseUseCaseAssumptions = UpcomingReleaseUseCaseAssumptions()

    @Test
    fun calendarScreen_test_append_progress_indicator() {
        val game = GameFixtures.slimeRancher2
        upcomingReleaseUseCaseAssumptions.assumeUpcomingReleasesSuccessfullyThenLoading(
            releases = (1..50).map {
                UpcomingRelease(game.copy(id = GameId("game-$it")), FEW_DAYS)
            },
        )
        gameFeed.root().performScrollToNode(GameFeedElement.appendingIndicatorMatcher())
    }
}

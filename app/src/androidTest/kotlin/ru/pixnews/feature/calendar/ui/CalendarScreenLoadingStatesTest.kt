/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import assertk.assertThat
import assertk.assertions.isGreaterThan
import co.touchlab.kermit.Logger
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.test.element.CalendarHeaderElement
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.di.ContributesTest
import ru.pixnews.foundation.instrumented.test.di.rule.InjectDependenciesRule
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.test.assumption.UpcomingReleaseUseCaseAssumptions
import java.net.NoRouteToHostException
import javax.inject.Inject
import ru.pixnews.foundation.ui.design.R as designR

@ContributesTest
class CalendarScreenLoadingStatesTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 30)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val upcomingReleaseUseCaseAssumptions = UpcomingReleaseUseCaseAssumptions(autoInitialize = false)
    val header = CalendarHeaderElement(composeTestRule)

    @Inject
    lateinit var logger: Logger

    @Test
    fun testInitialLoad() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseInitialLoading()
        composeTestRule.apply {
            onNode(hasTestTag(CalendarTestTag.INITIAL_LOADING_PLACEHOLDER)).assertExists()
            onNode(refreshingIndicatorMatcher).assertExists()
        }
    }

    @Test
    fun testNoInternet() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseFailure(
            error = NetworkRequestFailure.NetworkFailure(NoRouteToHostException()),
        )
        composeTestRule.apply {
            val chipsBottom = header.chipsRow.chipsLazyList()
                .getUnclippedBoundsInRoot().bottom
            val errorTextTop = onNodeWithText(composeTestRule.activity.getString(designR.string.title_error))
                .getUnclippedBoundsInRoot().top

            onNode(hasTestTag(CalendarTestTag.FAILURE_NO_INTERNET)).assertExists()
            onNode(refreshingIndicatorMatcher).assertDoesNotExist()

            assertThat(errorTextTop - chipsBottom).isGreaterThan(64.dp)
        }
    }

    @Test
    fun testServerFailure() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseFailure(
            error = NetworkRequestFailure.ApiFailure(NoRouteToHostException()),
        )
        composeTestRule.apply {
            val chipsBottom = header.chipsRow.chipsLazyList()
                .getUnclippedBoundsInRoot().bottom
            val errorTextTop = onNodeWithText(composeTestRule.activity.getString(designR.string.title_error))
                .getUnclippedBoundsInRoot().top

            onNode(hasTestTag(CalendarTestTag.FAILURE_OTHER_NETWORK_ERROR)).assertExists()
            onNode(refreshingIndicatorMatcher).assertDoesNotExist()
            assertThat(errorTextTop - chipsBottom).isGreaterThan(64.dp)
        }
    }

    companion object {
        val refreshingIndicatorMatcher: SemanticsMatcher = hasTestTag(CalendarTestTag.LOADING_OVERLAY)
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.root

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.touchlab.kermit.Logger
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.feature.navigation.BottomBarElement
import ru.pixnews.feature.root.TopLevelDestination.CALENDAR
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.di.ContributesTest
import ru.pixnews.foundation.instrumented.test.di.rule.InjectDependenciesRule
import ru.pixnews.test.assumption.UpcomingReleaseUseCaseAssumptions
import javax.inject.Inject

@ContributesTest
class FirstScreenTest : BaseInstrumentedTest() {
    @get:Rule
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val upcomingReleaseUseCaseAssumptions = UpcomingReleaseUseCaseAssumptions()

    @Inject
    lateinit var logger: Logger
    private lateinit var bottomBar: BottomBarElement

    @Before
    fun setup() {
        bottomBar = BottomBarElement(composeTestRule)
    }

    @Test
    fun calendarScreen_shouldBeFirstScreen() {
        bottomBar.assertActiveTab(CALENDAR)
    }
}

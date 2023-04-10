/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.feature.calendar

import android.os.Build
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemGestures
import co.touchlab.kermit.Logger
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import ru.pixnews.MainActivity
import ru.pixnews.feature.calendar.test.element.CalendarHeaderElement
import ru.pixnews.feature.calendar.test.element.GameFeedElement
import ru.pixnews.foundation.di.instrumented.test.ContributesTest
import ru.pixnews.foundation.di.instrumented.test.rule.InjectDependenciesRule
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode.GESTURAL
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode.THREE_BUTTON
import ru.pixnews.library.android.utils.rootView
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

@RunWith(Parameterized::class)
@ContributesTest
class CalendarScreenVerticalPaddingsTest(
    private val testSpec: TestCaseDeviceSpec,
) : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val systemNavigationRule = SystemNavigationRule(testSpec.navigationMode)

    @get:Rule(order = 30)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private var leftGuideline: Dp = (-1).dp
    private var rightGuideline: Dp = (-1).dp
    private val screenWidth: Dp by lazy(NONE) {
        with(composeTestRule.density) {
            composeTestRule.activity.window.decorView.width.toDp()
        }
    }
    private var calendarHeader = CalendarHeaderElement(composeTestRule)
    private var gameFeed = GameFeedElement(composeTestRule)

    @Inject
    lateinit var logger: Logger

    @Before
    fun setup() {
        if (testSpec.navigationMode == GESTURAL) {
            assumeTrue(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        }
        assumeTrue(
            "Device width ${screenWidth}dp outside the tested range ${testSpec.screenWidthDp}",
            screenWidth in testSpec.screenWidthDp,
        )

        ViewCompat.getRootWindowInsets(composeTestRule.activity.rootView)!!.also { insets ->
            val expectedPadding = testSpec.expectedHorizontalPadding(insets, composeTestRule.density)
            leftGuideline = expectedPadding
            rightGuideline = screenWidth - expectedPadding
            logger.i { "Expected padding: $expectedPadding. Window insets: ${insets.toWindowInsets()}" }
        }
    }

    @Test
    fun calendarScreen_mainElements_shouldHaveCorrectPaddings() {
        with(gameFeed.majorReleases) {
            waitTitle()
            title().assertLeftPositionInRootIsEqualTo(leftGuideline)
        }

        with(gameFeed) {
            scrollToDateSubheader()
            dateSubheader().assertLeftPositionInRootIsEqualTo(leftGuideline)

            scrollToGameCard()
            with(gameCard().getUnclippedBoundsInRoot()) {
                left.assertIsEqualTo(leftGuideline, "left")
                right.assertIsEqualTo(rightGuideline, "right")
            }
        }
    }

    @Test
    fun calendarScreen_dateSelectionHeader_shouldHaveCorrectPaddings() {
        // Text of the month selector button should be aligned to the left guideline
        calendarHeader.dateSelectionHeader.yearMonthPicker(useUnmergedTree = true)
            .onChildren()
            .filterToOne(SemanticsMatcher.keyIsDefined(SemanticsProperties.Text))
            .assertLeftPositionInRootIsEqualTo(leftGuideline)
    }

    @Test
    fun calendarScreen_weekDaysRow_shouldHaveCorrectPaddings() {
        // Button with first day of week should be aligned to the left guideline
        calendarHeader.dateSelectionHeader.weekDaysRoot()
            .onChildren()
            .filter(hasClickAction())
            .onFirst()
            .assertLeftPositionInRootIsEqualTo(leftGuideline)

        // Button with last day of week should be aligned to the right guideline
        calendarHeader.dateSelectionHeader.weekDaysRoot()
            .onChildren()
            .filter(hasClickAction())
            .onLast()
            .getUnclippedBoundsInRoot()
            .right
            .assertIsEqualTo(rightGuideline, "right")
    }

    @Test
    fun calendarScreen_chipsRow_shouldHaveCorrectPaddings() {
        // Chips row should not be clipped by left padding
        calendarHeader.chipsRow.chipsLazyList().assertLeftPositionInRootIsEqualTo(0.dp)

        // First chip should be aligned to the left guideline
        calendarHeader.chipsRow.chipsButtons()
            .onFirst()
            .assertLeftPositionInRootIsEqualTo(leftGuideline)

        // List/Grid selection button icon should be aligned on right guideline
        calendarHeader.chipsRow.gridButton(useUnmergedTree = true)
            .onChildren()
            .filterToOne(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
            .getUnclippedBoundsInRoot()
            .right
            .assertIsEqualTo(rightGuideline, "right")
    }

    data class TestCaseDeviceSpec(
        val screenWidthDp: ClosedRange<Dp>,
        val navigationMode: NavigationMode = THREE_BUTTON,
        val expectedHorizontalPadding: (WindowInsetsCompat, Density) -> Dp = { _, _ -> 16.dp },
    ) {
        override fun toString(): String = "device width: $screenWidthDp, navigation: $navigationMode"
    }

    companion object {
        private val paddingOnGesturalNavigation: (WindowInsetsCompat, Density) -> Dp = { insets, density ->
            val gestures = insets.getInsetsIgnoringVisibility(systemGestures())
            with(density) {
                maxOf(
                    gestures.left.toDp(),
                    gestures.right.toDp(),
                    16.dp,
                )
            }
        }

        @JvmStatic
        @Parameters(name = "{index} {0}")
        fun testCases(): List<TestCaseDeviceSpec> = listOf(
            TestCaseDeviceSpec(
                screenWidthDp = 320.dp..600.dp,
                navigationMode = THREE_BUTTON,
                expectedHorizontalPadding = { _, _ -> 16.dp },
            ),
            TestCaseDeviceSpec(
                screenWidthDp = 320.dp..600.dp,
                navigationMode = GESTURAL,
                expectedHorizontalPadding = paddingOnGesturalNavigation,
            ),
        )
    }
}

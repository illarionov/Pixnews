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
package ru.pixnews.features.calendar

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import assertk.assertThat
import assertk.assertions.isLessThanOrEqualTo
import co.touchlab.kermit.Logger
import com.google.accompanist.testharness.TestHarness
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.features.root.PixnewsRootContent
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.instrumented.testing.rule.InjectDependenciesRule
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import javax.inject.Inject

class CalendarFeedWidthOnMediumSizeTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val gameFeed: SemanticsNodeInteraction
        get() = composeTestRule.onNode(hasTestTag("calendar:content:lazy_list"))

    private val gameCardInFeed: SemanticsNodeInteraction
        get() = composeTestRule.onNode(CalendarScreenPaddingsTest.gameCardInFeedMatcher)

    private var screenWidth: Dp = (-1).dp

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var appConfig: AppConfig

    @Before
    fun setup() {
        composeTestRule.setContent {
            TestHarness(
                size = DpSize(width = 768.dp, height = 1024.dp),
            ) {
                screenWidth = with(LocalDensity.current) {
                    composeTestRule.activity.window.decorView.width.toDp()
                }
                PixnewsRootContent(
                    appConfig = appConfig,
                )
            }
        }
    }

    @Test
    fun calendarScreen_gameFeed_shouldHaveMaxWeight() {
        gameFeed.performScrollToNode(CalendarScreenPaddingsTest.gameCardInFeedMatcher)

        val bounds = gameCardInFeed.getUnclippedBoundsInRoot()
        assertThat(bounds.width).isLessThanOrEqualTo(552.dp)
        bounds.left.assertIsEqualTo((screenWidth - bounds.width) / 2, "horizontal padding")
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
import ru.pixnews.feature.calendar.test.element.GameFeedElement
import ru.pixnews.feature.root.PixnewsRootContent
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.di.ContributesTest
import ru.pixnews.foundation.instrumented.test.di.rule.InjectDependenciesRule
import javax.inject.Inject

@ContributesTest
class CalendarFeedWidthOnMediumSizeTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val gameFeed = GameFeedElement(composeTestRule)
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
    fun calendarScreen_gameFeed_shouldHaveMaxWidth() {
        gameFeed.scrollToGameCard()
        val bounds = gameFeed.gameCard().getUnclippedBoundsInRoot()

        assertThat(bounds.width).isLessThanOrEqualTo(552.dp)
        bounds.left.assertIsEqualTo((screenWidth - bounds.width) / 2, "horizontal padding")
    }
}

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
package ru.pixnews.features.root

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.touchlab.kermit.Logger
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.features.navigation.BottomBarElement
import ru.pixnews.features.root.TopLevelDestination.CALENDAR
import ru.pixnews.foundation.di.instrumented.testing.rule.InjectDependenciesRule
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import javax.inject.Inject

class FirstScreenTest : BaseInstrumentedTest() {
    @get:Rule
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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

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
package ru.pixnews.foundation.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.testing.util.HierarchyDumper

@RunWith(Enclosed::class)
public class ThemeTest : BaseInstrumentedTest() {
    @RunWith(Parameterized::class)
    internal class ValidateColorScheme(
        val testCase: TestCase,
    ) {
        @get:Rule
        val composeTestRule = createComposeRule()

        @Test
        fun colorSchemeShouldBeExpected() {
            composeTestRule.setContent {
                PixnewsTheme(
                    useDarkTheme = testCase.useDarkTheme,
                    useDynamicColor = testCase.useDynamicColor,
                ) {
                    assertColorSchemesEqual(testCase.expectedColorScheme(), MaterialTheme.colorScheme)
                }
            }
            HierarchyDumper.printAllToLog()
            HierarchyDumper.printToLog(composeTestRule.onRoot())
        }

        data class TestCase(
            val useDarkTheme: Boolean,
            val useDynamicColor: Boolean,
            val expectedColorScheme: @Composable () -> ColorScheme,
        ) {
            override fun toString(): String {
                return "useDark|useDynamic: $useDarkTheme|$useDynamicColor)"
            }
        }

        companion object {
            @JvmStatic
            @Parameters(name = "{index} {0}")
            fun testCases(): List<TestCase> = listOf(
                TestCase(
                    useDarkTheme = false,
                    useDynamicColor = false,
                    expectedColorScheme = { LightColors },
                ),
                TestCase(
                    useDarkTheme = true,
                    useDynamicColor = false,
                    expectedColorScheme = { DarkColors },
                ),
                TestCase(
                    useDarkTheme = false,
                    useDynamicColor = true,
                    expectedColorScheme = { dynamicLightColorSchemeWithFallback() },
                ),
                TestCase(
                    useDarkTheme = true,
                    useDynamicColor = true,
                    expectedColorScheme = { dynamicDarkColorSchemeWithFallback() },
                ),
            )
        }
    }
}

private fun assertColorSchemesEqual(
    expectedColorScheme: ColorScheme,
    actualColorScheme: ColorScheme,
) {
    listOf(
        ColorScheme::primary,
        ColorScheme::onPrimary,
        ColorScheme::primaryContainer,
        ColorScheme::onPrimary,
        ColorScheme::secondary,
        ColorScheme::onSecondary,
        ColorScheme::secondaryContainer,
        ColorScheme::onSecondaryContainer,
        ColorScheme::tertiary,
        ColorScheme::onTertiary,
        ColorScheme::tertiaryContainer,
        ColorScheme::onTertiaryContainer,
        ColorScheme::error,
        ColorScheme::errorContainer,
        ColorScheme::onError,
        ColorScheme::onErrorContainer,
        ColorScheme::background,
        ColorScheme::onBackground,
        ColorScheme::surface,
        ColorScheme::onSurface,
        ColorScheme::surfaceVariant,
        ColorScheme::onSurfaceVariant,
        ColorScheme::outline,
        ColorScheme::inverseOnSurface,
        ColorScheme::inverseSurface,
        ColorScheme::inversePrimary,
        ColorScheme::surfaceTint,
        ColorScheme::outlineVariant,
        ColorScheme::scrim,
    ).forEach { colorProperty ->
        assertEquals(colorProperty(expectedColorScheme), colorProperty(actualColorScheme))
    }
}

@Composable
private fun dynamicLightColorSchemeWithFallback(): ColorScheme {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicLightColorScheme(LocalContext.current)
    } else {
        LightColors
    }
}

@Composable
private fun dynamicDarkColorSchemeWithFallback(): ColorScheme {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicDarkColorScheme(LocalContext.current)
    } else {
        DarkColors
    }
}

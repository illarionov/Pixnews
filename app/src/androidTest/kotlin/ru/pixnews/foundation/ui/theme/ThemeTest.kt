/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest

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

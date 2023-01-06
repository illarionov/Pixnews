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
@file:OptIn(ExperimentalTextApi::class)

package ru.pixnews.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import ru.pixnews.ui.theme.R as UiThemeR

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = UiThemeR.array.com_google_android_gms_fonts_certs,
)

private val rubikFontName = GoogleFont("Rubik")
private val openSansFontName = GoogleFont("Open Sans")

public val rubikFontFamily: FontFamily = FontFamily(
    Font(
        googleFont = rubikFontName,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        googleFont = rubikFontName,
        fontProvider = provider,
        weight = FontWeight.Light,
        style = FontStyle.Normal,
    ),
    Font(
        googleFont = rubikFontName,
        fontProvider = provider,
        weight = FontWeight.Medium,
        style = FontStyle.Normal,
    ),
)

public val openSansFontFamily: FontFamily = FontFamily(
    Font(
        googleFont = openSansFontName,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        googleFont = openSansFontName,
        fontProvider = provider,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal,
    ),
)

public val AppTypography: Typography = Typography(
    labelLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
        fontSize = 13.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.8.sp,
        lineHeight = 16.sp,
        fontSize = 10.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.3.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.3.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Light,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Light,
        letterSpacing = (-0.5).sp,
        lineHeight = 36.sp,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Light,
        letterSpacing = (-0.5).sp,
        lineHeight = 32.sp,
        fontSize = 24.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 64.sp,
        fontSize = 55.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 52.sp,
        fontSize = 43.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        fontSize = 35.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.15).sp,
        lineHeight = 28.sp,
        fontSize = 21.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = rubikFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.1).sp,
        lineHeight = 20.sp,
        fontSize = 14.sp,
    ),
)

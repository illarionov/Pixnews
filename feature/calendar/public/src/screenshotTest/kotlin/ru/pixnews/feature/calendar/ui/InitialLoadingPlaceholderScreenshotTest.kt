/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.feature.calendar.ui

import androidx.compose.runtime.Composable
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.ScreenshotTestPreview

class InitialLoadingPlaceholderScreenshotTest {
    @Composable
    @ScreenshotTestPreview
    fun InitialLoadingPlaceholderTest() {
        PixnewsTheme(useDynamicColor = false) {
            InitialLoadingPlaceholder()
        }
    }
}

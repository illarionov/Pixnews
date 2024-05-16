/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.feature.calendar.ui.failure

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.ScreenshotTestPreview

class OtherNetworkErrorScreenshotTest {
    @Composable
    @ScreenshotTestPreview
    fun OtherNetworkErrorTest(
        @PreviewParameter(RefreshActiveProvider::class) refreshActive: Boolean,
    ) {
        PixnewsTheme(useDynamicColor = false) {
            OtherNetworkError(
                onRefreshClicked = { },
                refreshActive = { refreshActive },
            )
        }
    }

    class RefreshActiveProvider : PreviewParameterProvider<Boolean> {
        override val values: Sequence<Boolean> = sequenceOf(false, true)
    }
}

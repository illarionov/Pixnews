/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.foundation.ui.design.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.ScreenshotTestPreview

class PixnewsGameCardGridSmallScreenshotTest {
    @Composable
    @ScreenshotTestPreview
    fun CheckCalendarScreenHeader(
        @PreviewParameter(SmallGameCardProvider::class) gameUiModel: PixnewsGameCardGridSmallUiModel,
    ) {
        FakeImageLoader.setupFakeImageLoader()
        PixnewsTheme(useDynamicColor = false) {
            PixnewsGameCardGridSmall(
                game = gameUiModel,
                onClick = {},
            )
        }
    }

    class SmallGameCardProvider : PreviewParameterProvider<PixnewsGameCardGridSmallUiModel> {
        override val values: Sequence<PixnewsGameCardGridSmallUiModel> = sequenceOf(
            GameFixtures.hytale.toGameCardGridMajorReleasesUiItem(favourite = false),
            GameFixtures.theLostWild.toGameCardGridMajorReleasesUiItem(favourite = true),
        )
    }
}

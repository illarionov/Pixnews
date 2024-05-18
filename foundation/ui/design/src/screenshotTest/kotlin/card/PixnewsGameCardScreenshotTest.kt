/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.foundation.ui.design.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import ru.pixnews.debuglayout.DebugLayout.Companion.debugLayout
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardVariant.TITLE_GENRES_PLATFORMS
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardVariant.TITLE_RELEASE_DATE
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.ScreenshotTestPreview

class PixnewsGameCardScreenshotTest {
    @Composable
    @ScreenshotTestPreview
    fun CheckGameListCard_Default(
        @PreviewParameter(GameCardProvider::class) gameUiModel: PixnewsGameCardUiModel,
    ) = TestPixnewsGameCard(gameUiModel, TITLE_GENRES_PLATFORMS)

    @Composable
    @ScreenshotTestPreview
    fun CheckGameListCard_TitleReleaseDate(
        @PreviewParameter(GameCardProvider::class) gameUiModel: PixnewsGameCardUiModel,
    ) = TestPixnewsGameCard(gameUiModel, TITLE_RELEASE_DATE)

    @Composable
    @ScreenshotTestPreview
    fun CheckGameListCard_NoPlatforms(
        @PreviewParameter(GameCardProvider::class) gameUiModel: PixnewsGameCardUiModel,
    ) {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val platforms: ImmutableSet<GamePlatform> = persistentSetOf()
        }
        TestPixnewsGameCard(game, TITLE_GENRES_PLATFORMS)
    }

    @Composable
    @ScreenshotTestPreview
    fun CheckGameListCard_Default_NoGenres(
        @PreviewParameter(GameCardProvider::class) gameUiModel: PixnewsGameCardUiModel,
    ) {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val genres: String get() = ""
        }
        TestPixnewsGameCard(game, TITLE_GENRES_PLATFORMS)
    }

    @Composable
    @ScreenshotTestPreview
    fun CheckGameListCard_NoGenres(
        @PreviewParameter(GameCardProvider::class) gameUiModel: PixnewsGameCardUiModel,
    ) {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val genres: String get() = ""
        }
        TestPixnewsGameCard(game, TITLE_RELEASE_DATE)
    }

    @Composable
    private fun TestPixnewsGameCard(
        game: PixnewsGameCardUiModel,
        variant: PixnewsGameCardVariant,
    ) {
        FakeImageLoader.setupFakeImageLoader()

        PixnewsTheme(useDynamicColor = false) {
            PixnewsGameCard(
                modifier = Modifier.gameCardDebugLayout(),
                game = game,
                variant = variant,
                onClick = {},
                onFavouriteClick = {},
            )
        }
    }

    class GameCardProvider : PreviewParameterProvider<PixnewsGameCardUiModel> {
        override val values: Sequence<PixnewsGameCardUiModel> = sequenceOf(
            GameFixtures.hytale.toGameCardItem(favourite = false),
            GameFixtures.theLostWild.toGameCardItem(favourite = true),
        )
    }

    private companion object {
        private fun Modifier.gameCardDebugLayout(): Modifier = debugLayout {
            grid(size = 8.dp)
        }
    }
}

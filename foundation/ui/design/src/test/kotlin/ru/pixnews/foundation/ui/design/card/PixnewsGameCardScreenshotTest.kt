/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.card

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.debuglayout.DebugLayout.Companion.debugLayout
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardVariant.TITLE_GENRES_PLATFORMS
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardVariant.TITLE_RELEASE_DATE
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoaderRule
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@RunWith(Parameterized::class)
class PixnewsGameCardScreenshotTest(
    val gameUiModel: PixnewsGameCardUiModel,
) {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SHRINK,
        showSystemUi = false,
    )

    @get:Rule
    val fakeImageLoafer = FakeImageLoaderRule(paparazzi::context)

    @Test
    fun checkGameListCard_Default() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    modifier = Modifier
                        .gameCardDebugLayout(),
                    game = gameUiModel,
                    variant = TITLE_GENRES_PLATFORMS,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    @Test
    fun checkGameListCard_TitleReleaseDate() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    modifier = Modifier.gameCardDebugLayout(),
                    game = gameUiModel,
                    variant = TITLE_RELEASE_DATE,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    @Test
    fun checkGameListCard_NoPlatforms() {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val platforms: ImmutableSet<GamePlatform> = persistentSetOf()
        }

        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    modifier = Modifier.gameCardDebugLayout(),
                    game = game,
                    variant = TITLE_GENRES_PLATFORMS,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    @Test
    fun checkGameListCard_Default_NoGenres() {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val genres: String get() = ""
        }

        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    modifier = Modifier.gameCardDebugLayout(),
                    game = game,
                    variant = TITLE_GENRES_PLATFORMS,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    @Test
    fun checkGameListCard__NoGenres() {
        val game = object : PixnewsGameCardUiModel by gameUiModel {
            override val genres: String get() = ""
        }

        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    modifier = Modifier.gameCardDebugLayout(),
                    game = game,
                    variant = TITLE_RELEASE_DATE,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<PixnewsGameCardUiModel> {
            return listOf(
                GameFixtures.hytale.toGameCardItem(favourite = false),
                GameFixtures.theLostWild.toGameCardItem(favourite = true),
            )
        }

        private fun Modifier.gameCardDebugLayout(): Modifier = debugLayout {
            grid(size = 8.dp)
        }
    }
}

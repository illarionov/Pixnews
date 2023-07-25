/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.design.card

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoaderRule
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@RunWith(Parameterized::class)
class PixnewsGameCardGridSmallScreenshotTest(
    val gameUiModel: PixnewsGameCardGridSmallUiModel,
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
    fun checkCalendarScreenHeader() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCardGridSmall(
                    game = gameUiModel,
                    onClick = {},
                )
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<PixnewsGameCardGridSmallUiModel> {
            return listOf(
                GameFixtures.hytale.toGameCardGridMajorReleasesUiItem(favourite = false),
                GameFixtures.theLostWild.toGameCardGridMajorReleasesUiItem(favourite = true),
            )
        }
    }
}

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
    fun checkCalendarScreenHeader() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    game = gameUiModel,
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
    }
}

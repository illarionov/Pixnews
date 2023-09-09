/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlatformFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.android
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.commodoreC64
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.ios
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.linux
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.mac
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.n3ds
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.ps4
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.ps5
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.psVita
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.switch
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.win
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.xBoxSeriesX
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.xboxOne
import ru.pixnews.igdbclient.model.Platform as IgdbPlatform

class IgdbPlatformConverterTest {
    @ParameterizedTest
    @MethodSource("platformConverterTestSource")
    fun `toGamePlatform should convert platforms`(testData: Pair<IgdbPlatform, GamePlatform>) {
        val result = testData.first.toGamePlatform()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toGamePlatform should convert platforms with minimal fields`() {
        val metaQuest3 = IgdbPlatform(
            id = 471,
            name = "Meta Quest 3",
            slug = "meta-quest-3",
        )
        val result = metaQuest3.toGamePlatform()
        result shouldBeEqual GamePlatform.Other("Meta Quest 3")
    }

    @Test
    fun `toGamePlatform should fail when required fields are not defined`() {
        shouldThrow<IllegalArgumentException> { IgdbPlatform(471).toGamePlatform() }
        shouldThrow<IllegalArgumentException> {
            IgdbPlatform(
                id = 471,
                name = "Meta Quest 3",
            ).toGamePlatform()
        }
    }

    internal companion object {
        @JvmStatic
        fun platformConverterTestSource(): List<Pair<IgdbPlatform, GamePlatform>> = listOf(
            IgdbPlatformFixtures.win to GamePlatform.Windows,
            IgdbPlatformFixtures.mac to GamePlatform.Macos,
            IgdbPlatformFixtures.linux to GamePlatform.Linux,
            IgdbPlatformFixtures.ps4 to GamePlatform.PlayStation4,
            IgdbPlatformFixtures.ps5 to GamePlatform.PlayStation5,
            IgdbPlatformFixtures.psVita to GamePlatform.PsVita,
            IgdbPlatformFixtures.xboxOne to GamePlatform.XboxOne,
            IgdbPlatformFixtures.xBoxSeriesX to GamePlatform.XboxSeriesXs,
            IgdbPlatformFixtures.switch to GamePlatform.NintendoSwitch,
            IgdbPlatformFixtures.n3ds to GamePlatform.Nintendo3Ds,
            IgdbPlatformFixtures.ios to GamePlatform.Ios,
            IgdbPlatformFixtures.android to GamePlatform.Android,
            IgdbPlatformFixtures.commodoreC64 to GamePlatform.Other("Commodore C64/128/MAX"),
        )
    }
}

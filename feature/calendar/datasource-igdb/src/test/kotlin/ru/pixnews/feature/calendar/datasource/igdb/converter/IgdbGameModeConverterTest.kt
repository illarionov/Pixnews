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
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameModeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.battleRoyale
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.coOperative
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.massivelyMultiplayerOnlineMmo
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.multiplayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.singlePlayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.splitScreen
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

class IgdbGameModeConverterTest {
    @ParameterizedTest
    @MethodSource("gameModeTestSource")
    fun `toGameMode() should convert platforms`(testData: Pair<IgdbGameMode, GameMode>) {
        val result = testData.first.toGameMode()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toGameMode() should fail when required fields are not requested`() {
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 0, name = "Test Game Mode", slug = "test-game-mode").toGameMode()
        }
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 100, name = "", slug = "test-game-mode").toGameMode()
        }
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 100, name = "Test Game Mode", slug = "").toGameMode()
        }
    }

    companion object {
        @JvmStatic
        fun gameModeTestSource(): List<Pair<IgdbGameMode, GameMode>> = listOf(
            IgdbGameModeFixtures.singlePlayer to GameMode.SinglePlayer,
            IgdbGameModeFixtures.multiplayer to GameMode.Multiplayer,
            IgdbGameModeFixtures.coOperative to GameMode.CoOperative,
            IgdbGameModeFixtures.splitScreen to GameMode.SplitScreen,
            IgdbGameModeFixtures.massivelyMultiplayerOnlineMmo to GameMode.Mmo,
            IgdbGameModeFixtures.battleRoyale to GameMode.BattleRoyale,
            IgdbGameMode(
                id = 100,
                name = "Test Game Mode",
                slug = "test-game-mode",
            ) to GameMode.Other("Test Game Mode"),
        )
    }
}

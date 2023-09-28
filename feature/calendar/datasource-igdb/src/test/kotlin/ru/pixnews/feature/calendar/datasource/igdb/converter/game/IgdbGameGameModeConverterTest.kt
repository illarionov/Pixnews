/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.collections.immutable.persistentSetOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GameMode.BattleRoyale
import ru.pixnews.domain.model.game.GameMode.CoOperative
import ru.pixnews.domain.model.game.GameMode.Mmo
import ru.pixnews.domain.model.game.GameMode.Multiplayer
import ru.pixnews.domain.model.game.GameMode.Other
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GameMode.SplitScreen
import ru.pixnews.domain.model.id.GameModeId
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameGameModeConverter.convert
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameModeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.battleRoyale
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.coOperative
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.massivelyMultiplayerOnlineMmo
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.multiplayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.singlePlayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.splitScreen
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameModeId
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

class IgdbGameGameModeConverterTest {
    @ParameterizedTest
    @MethodSource("gameModeTestSource")
    fun `should convert platforms`(testData: Pair<IgdbGameMode, Ref<GameMode>>) {
        val result = convert(
            Game(game_modes = listOf(testData.first)),
        )
        result shouldBeEqual persistentSetOf(testData.second)
    }

    @Test
    fun `toGameMode() should fail when required fields are not requested`() {
        shouldThrow<IllegalArgumentException> {
            convert(
                Game(
                    game_modes = listOf(
                        IgdbGameMode(id = 0, name = "Test Game Mode", slug = "test-game-mode"),
                    ),
                ),
            )
        }
        shouldThrow<IllegalArgumentException> {
            convert(
                Game(
                    game_modes = listOf(IgdbGameMode(id = 100, name = "", slug = "test-game-mode")),
                ),
            )
        }
        shouldThrow<IllegalArgumentException> {
            convert(
                Game(
                    game_modes = listOf(IgdbGameMode(id = 100, name = "Test Game Mode", slug = "")),
                ),
            )
        }
    }

    companion object {
        @JvmStatic
        fun gameModeTestSource(): List<Pair<IgdbGameMode, Ref<GameMode>>> {
            val fullObjects = listOf(
                IgdbGameModeFixtures.singlePlayer to FullObject(SinglePlayer),
                IgdbGameModeFixtures.multiplayer to FullObject(Multiplayer),
                IgdbGameModeFixtures.coOperative to FullObject(CoOperative),
                IgdbGameModeFixtures.splitScreen to FullObject(SplitScreen),
                IgdbGameModeFixtures.massivelyMultiplayerOnlineMmo to FullObject(Mmo),
                IgdbGameModeFixtures.battleRoyale to FullObject(BattleRoyale),
            )

            val ids = fullObjects.map { (igdbGameMode, gameModeRef) ->
                IgdbGameMode(id = igdbGameMode.id) to gameModeRef
            }

            val slugs = fullObjects.map { (igdbGameMode, gameModeRef) ->
                IgdbGameMode(
                    id = igdbGameMode.id + 100,
                    slug = igdbGameMode.slug,
                    name = igdbGameMode.name,
                ) to gameModeRef
            }

            val referencesWithUnknownIds = listOf(
                IgdbGameMode(id = 110) to Ref.Id<GameMode, GameModeId>(IgdbGameModeId(110)),
            )

            return fullObjects + ids + slugs + referencesWithUnknownIds + listOf(
                IgdbGameMode(
                    id = 100,
                    name = "Test Game Mode",
                    slug = "test-game-mode",
                ) to FullObject(Other("Test Game Mode")),
            )
        }
    }
}

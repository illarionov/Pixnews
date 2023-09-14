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
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.id.GameModeId
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameModeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.battleRoyale
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.coOperative
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.massivelyMultiplayerOnlineMmo
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.multiplayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.singlePlayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.splitScreen
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameModeId
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

class IgdbGameModeConverterTest {
    @ParameterizedTest
    @MethodSource("gameModeTestSource")
    fun `toGameModeRef() should convert platforms`(testData: Pair<IgdbGameMode, Ref<GameMode>>) {
        val result = testData.first.toGameModeRef()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toGameMode() should fail when required fields are not requested`() {
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 0, name = "Test Game Mode", slug = "test-game-mode").toGameModeRef()
        }
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 100, name = "", slug = "test-game-mode").toGameModeRef()
        }
        shouldThrow<IllegalArgumentException> {
            IgdbGameMode(id = 100, name = "Test Game Mode", slug = "").toGameModeRef()
        }
    }

    companion object {
        @JvmStatic
        fun gameModeTestSource(): List<Pair<IgdbGameMode, Ref<GameMode>>> {
            val fullObjects = listOf(
                IgdbGameModeFixtures.singlePlayer to FullObject(SinglePlayer),
                IgdbGameModeFixtures.multiplayer to FullObject(GameMode.Multiplayer),
                IgdbGameModeFixtures.coOperative to FullObject(GameMode.CoOperative),
                IgdbGameModeFixtures.splitScreen to FullObject(GameMode.SplitScreen),
                IgdbGameModeFixtures.massivelyMultiplayerOnlineMmo to FullObject(GameMode.Mmo),
                IgdbGameModeFixtures.battleRoyale to FullObject(GameMode.BattleRoyale),
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
                ) to FullObject(GameMode.Other("Test Game Mode")),
            )
        }
    }
}

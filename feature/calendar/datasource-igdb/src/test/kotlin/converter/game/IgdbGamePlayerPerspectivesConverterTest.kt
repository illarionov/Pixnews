/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.game.PlayerPerspective.Auditory
import ru.pixnews.domain.model.game.PlayerPerspective.FirstPerson
import ru.pixnews.domain.model.game.PlayerPerspective.Isometric
import ru.pixnews.domain.model.game.PlayerPerspective.Other
import ru.pixnews.domain.model.game.PlayerPerspective.SideView
import ru.pixnews.domain.model.game.PlayerPerspective.Text
import ru.pixnews.domain.model.game.PlayerPerspective.ThirdPerson
import ru.pixnews.domain.model.game.PlayerPerspective.Vr
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.Ref.Id
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGamePlayerPerspectivesConverter.convertToPlayerPerspectiveRef
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlayerPerspectiveFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.auditory
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.firstPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.isometric
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.sideView
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.text
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.thirdPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.virtualReality
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbPlayerPerspectiveId
import ru.pixnews.igdbclient.model.PlayerPerspective

class IgdbGamePlayerPerspectivesConverterTest {
    @ParameterizedTest
    @MethodSource("toPlayerPerspectiveTestSource")
    fun `IgdbPlayerPerspectivesConverter should convert player perspectives`(
        testData: Pair<PlayerPerspective, Ref<ru.pixnews.domain.model.game.PlayerPerspective>>,
    ) {
        val result = convertToPlayerPerspectiveRef(testData.first)
        result shouldBeEqual testData.second
    }

    @Test
    fun `IgdbPlayerPerspectivesConverter should throw on no fields requested`() {
        shouldThrow<IllegalArgumentException> {
            convertToPlayerPerspectiveRef(PlayerPerspective(id = 0))
        }
    }

    internal companion object {
        @JvmStatic
        @Suppress("MaxLineLength")
        fun toPlayerPerspectiveTestSource(): List<Pair<PlayerPerspective, Ref<ru.pixnews.domain.model.game.PlayerPerspective>>> {
            val fullObjects = listOf(
                IgdbPlayerPerspectiveFixtures.firstPerson to FullObject(FirstPerson),
                IgdbPlayerPerspectiveFixtures.thirdPerson to FullObject(ThirdPerson),
                IgdbPlayerPerspectiveFixtures.isometric to FullObject(Isometric),
                IgdbPlayerPerspectiveFixtures.sideView to FullObject(SideView),
                IgdbPlayerPerspectiveFixtures.text to FullObject(Text),
                IgdbPlayerPerspectiveFixtures.auditory to FullObject(Auditory),
                IgdbPlayerPerspectiveFixtures.virtualReality to FullObject(Vr),
            )
            val refs = fullObjects.map { (igdbPerspective, perspective) ->
                PlayerPerspective(id = igdbPerspective.id) to perspective
            }
            val slugs = fullObjects.map { (igdbPerspective, perspective) ->
                PlayerPerspective(
                    id = igdbPerspective.id + 1000,
                    slug = igdbPerspective.slug,
                ) to perspective
            }
            return fullObjects + refs + slugs + listOf(
                PlayerPerspective(
                    id = 100,
                    name = "Test Perspective",
                ) to FullObject(Other("Test Perspective")),
                PlayerPerspective(
                    id = 110,
                ) to Id(IgdbPlayerPerspectiveId(110)),
            )
        }
    }
}

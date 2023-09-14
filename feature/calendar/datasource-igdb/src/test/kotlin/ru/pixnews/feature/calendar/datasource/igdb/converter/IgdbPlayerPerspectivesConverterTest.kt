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
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlayerPerspectiveFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.auditory
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.firstPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.isometric
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.sideView
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.text
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.thirdPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.virtualReality
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbPlayerPerspectiveId
import ru.pixnews.igdbclient.model.PlayerPerspective as IgdbPlayerPerspective

class IgdbPlayerPerspectivesConverterTest {
    @ParameterizedTest
    @MethodSource("toPlayerPerspectiveTestSource")
    fun `toPlayerPerspectiveRef should convert player perspectives`(
        testData: Pair<IgdbPlayerPerspective, Ref<PlayerPerspective>>,
    ) {
        val result = testData.first.toPlayerPerspectiveRef()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toPlayerPerspective should throw on no fields requested`() {
        shouldThrow<IllegalArgumentException> {
            IgdbPlayerPerspective(id = 0).toPlayerPerspectiveRef()
        }
    }

    internal companion object {
        @JvmStatic
        @Suppress("MaxLineLength")
        fun toPlayerPerspectiveTestSource(): List<Pair<IgdbPlayerPerspective, Ref<PlayerPerspective>>> {
            val fullObjects = listOf(
                IgdbPlayerPerspectiveFixtures.firstPerson to FullObject(PlayerPerspective.FirstPerson),
                IgdbPlayerPerspectiveFixtures.thirdPerson to FullObject(PlayerPerspective.ThirdPerson),
                IgdbPlayerPerspectiveFixtures.isometric to FullObject(PlayerPerspective.Isometric),
                IgdbPlayerPerspectiveFixtures.sideView to FullObject(PlayerPerspective.SideView),
                IgdbPlayerPerspectiveFixtures.text to FullObject(PlayerPerspective.Text),
                IgdbPlayerPerspectiveFixtures.auditory to FullObject(PlayerPerspective.Auditory),
                IgdbPlayerPerspectiveFixtures.virtualReality to FullObject(PlayerPerspective.Vr),
            )
            val refs = fullObjects.map { (igdbPerspective, perspective) ->
                IgdbPlayerPerspective(id = igdbPerspective.id) to perspective
            }
            val slugs = fullObjects.map { (igdbPerspective, perspective) ->
                IgdbPlayerPerspective(
                    id = igdbPerspective.id + 1000,
                    slug = igdbPerspective.slug,
                ) to perspective
            }
            return fullObjects + refs + slugs + listOf(
                IgdbPlayerPerspective(
                    id = 100,
                    name = "Test Perspective",
                ) to FullObject(PlayerPerspective.Other("Test Perspective")),
                IgdbPlayerPerspective(
                    id = 110,
                ) to Ref.Id(IgdbPlayerPerspectiveId(110)),
            )
        }
    }
}

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
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlayerPerspectiveFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.auditory
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.firstPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.isometric
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.sideView
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.text
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.thirdPerson
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective.virtualReality
import ru.pixnews.igdbclient.model.PlayerPerspective as IgdbPlayerPerspective

class IgdbPlayerPerspectivesConverterTest {
    @ParameterizedTest
    @MethodSource("toPlayerPerspectiveTestSource")
    fun `toPlayerPerspective should convert player perspectives`(
        testData: Pair<IgdbPlayerPerspective, PlayerPerspective>,
    ) {
        val result = testData.first.toPlayerPerspective()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toPlayerPerspective should throw on no fields requested`() {
        shouldThrow<IllegalArgumentException> {
            IgdbPlayerPerspective(id = 0).toPlayerPerspective()
        }
    }

    internal companion object {
        @JvmStatic
        fun toPlayerPerspectiveTestSource(): List<Pair<IgdbPlayerPerspective, PlayerPerspective>> =
            listOf(
                IgdbPlayerPerspectiveFixtures.firstPerson to PlayerPerspective.FirstPerson,
                IgdbPlayerPerspectiveFixtures.thirdPerson to PlayerPerspective.ThirdPerson,
                IgdbPlayerPerspectiveFixtures.isometric to PlayerPerspective.Isometric,
                IgdbPlayerPerspectiveFixtures.sideView to PlayerPerspective.SideView,
                IgdbPlayerPerspectiveFixtures.text to PlayerPerspective.Text,
                IgdbPlayerPerspectiveFixtures.auditory to PlayerPerspective.Auditory,
                IgdbPlayerPerspectiveFixtures.virtualReality to PlayerPerspective.Vr,
                IgdbPlayerPerspective(
                    id = 100,
                    name = "Test Perspective",
                ) to PlayerPerspective.Other("Test Perspective"),
            )
    }
}

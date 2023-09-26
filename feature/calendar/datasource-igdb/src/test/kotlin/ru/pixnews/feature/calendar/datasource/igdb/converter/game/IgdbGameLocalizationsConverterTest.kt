/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentSetOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameLocalizationsConverter.convert
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbLanguageSupportFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.englishSubtitlesSupport
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.japaneseSoundSupport
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.spanishInterfaceSupport
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Language
import ru.pixnews.igdbclient.model.LanguageSupport
import ru.pixnews.igdbclient.model.LanguageSupportType

class IgdbGameLocalizationsConverterTest {
    @ParameterizedTest
    @MethodSource("languageSupportTestSource")
    fun `should convert language supports`(
        testData: Pair<List<LanguageSupport>, GameLocalizations?>,
    ) {
        val game = Game(language_supports = testData.first)
        val result = convert(game)
        result.shouldBe(testData.second)
    }

    @Test
    fun `should convert language supports with minimal fields`() {
        val languageSupport = listOf(
            LanguageSupport(
                language = Language(locale = "es-MX"),
                language_support_type = LanguageSupportType(id = 1),
            ),
        )

        val game = Game(language_supports = languageSupport)

        val result = convert(game)

        result.shouldNotBeNull().shouldBeEqual(
            GameLocalizations(
                sound = persistentSetOf(LanguageCode.SPANISH),
                text = persistentSetOf(),
            ),
        )
    }

    @Test
    fun `should fail when required fields are not requested`() {
        listOf(
            LanguageSupport(id = 1),
            LanguageSupport(
                language = null,
                language_support_type = LanguageSupportType(id = 1),
            ),
            LanguageSupport(
                language = Language(id = 1),
                language_support_type = LanguageSupportType(id = 1),
            ),
            LanguageSupport(
                language = Language(locale = "es-MX"),
                language_support_type = null,
            ),
            LanguageSupport(
                language = Language(locale = "es-MX"),
                language_support_type = LanguageSupportType(id = 0),
            ),
        ).forEach { languageSupport ->
            val game = Game(language_supports = listOf(languageSupport))
            shouldThrow<IllegalArgumentException> {
                convert(game)
            }
        }
    }

    private companion object {
        @JvmStatic
        fun languageSupportTestSource(): List<Pair<List<LanguageSupport>, GameLocalizations?>> =
            listOf(
                emptyList<LanguageSupport>() to null,
                listOf(
                    IgdbLanguageSupportFixtures.japaneseSoundSupport,
                    IgdbLanguageSupportFixtures.englishSubtitlesSupport,
                    IgdbLanguageSupportFixtures.spanishInterfaceSupport,
                ) to GameLocalizations(
                    sound = persistentSetOf(LanguageCode.from("jpn")),
                    text = persistentSetOf(
                        LanguageCode.ENGLISH,
                        LanguageCode.SPANISH,
                    ),
                ),
            )
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.collections.immutable.persistentSetOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbLanguageSupportFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.englishSubtitlesSupport
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.japaneseSoundSupport
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport.spanishInterfaceSupport
import ru.pixnews.igdbclient.model.LanguageSupportType
import ru.pixnews.igdbclient.model.Language as IgdbLanguage
import ru.pixnews.igdbclient.model.LanguageSupport as IgdbLanguageSupport

class IgdbLanguageSupportsConverterTest {
    @ParameterizedTest
    @MethodSource("languageSupportTestSource")
    fun `toGamePlatform should convert platforms`(testData: Pair<List<IgdbLanguageSupport>, GameLocalizations>) {
        val result = testData.first.toLocalizations()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toGamePlatform should convert platform with minimal fields`() {
        val languageSupport = listOf(
            IgdbLanguageSupport(
                language = IgdbLanguage(locale = "es-MX"),
                language_support_type = LanguageSupportType(id = 1),
            ),
        )

        val result = languageSupport.toLocalizations()

        result shouldBeEqual GameLocalizations(
            sound = persistentSetOf(LanguageCode.SPANISH),
            text = persistentSetOf(),
        )
    }

    @Test
    fun `toGamePlatform should fail when required fields are not requested`() {
        listOf(
            IgdbLanguageSupport(id = 1),
            IgdbLanguageSupport(
                language = null,
                language_support_type = LanguageSupportType(id = 1),
            ),
            IgdbLanguageSupport(
                language = IgdbLanguage(id = 1),
                language_support_type = LanguageSupportType(id = 1),
            ),
            IgdbLanguageSupport(
                language = IgdbLanguage(locale = "es-MX"),
                language_support_type = null,
            ),
            IgdbLanguageSupport(
                language = IgdbLanguage(locale = "es-MX"),
                language_support_type = LanguageSupportType(id = 0),
            ),
        ).forEach { languageSupport ->
            shouldThrow<IllegalArgumentException> {
                listOf(languageSupport).toLocalizations()
            }
        }
    }

    private companion object {
        @JvmStatic
        fun languageSupportTestSource(): List<Pair<List<IgdbLanguageSupport>, GameLocalizations>> =
            listOf(
                emptyList<IgdbLanguageSupport>() to GameLocalizations(
                    sound = persistentSetOf(),
                    text = persistentSetOf(),
                ),
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

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.feature.calendar.datasource.igdb.converter.LanguageSupportType.SOUND
import ru.pixnews.feature.calendar.datasource.igdb.converter.LanguageSupportType.TEXT
import ru.pixnews.igdbclient.model.LanguageSupport
import java.util.Locale
import java.util.MissingResourceException
import ru.pixnews.igdbclient.model.LanguageSupport as IgdbLanguageSupport

public fun Collection<IgdbLanguageSupport>.toLocalizations(): GameLocalizations {
    val audioLocalizations: MutableSet<LanguageCode> = mutableSetOf()
    val textLocalizations: MutableSet<LanguageCode> = mutableSetOf()

    this.mapNotNull(LanguageSupport::parseTypeCode)
        .forEach { (languageSupportType, languageCode) ->
            when (languageSupportType) {
                SOUND -> audioLocalizations.add(languageCode)
                TEXT -> textLocalizations.add(languageCode)
            }
        }

    return GameLocalizations(
        sound = audioLocalizations.toImmutableSet(),
        text = textLocalizations.toImmutableSet(),
    )
}

private enum class LanguageSupportType {
    SOUND, TEXT
}

private fun IgdbLanguageSupport.parseTypeCode(): Pair<LanguageSupportType, LanguageCode>? {
    val igdbLanguageSupportType = requireFieldInitialized(
        "language_supports.language_support_type",
        language_support_type?.id ?: 0,
    )

    @Suppress("MagicNumber")
    val languageSupportType = when (igdbLanguageSupportType) {
        /* Audio */
        1L -> SOUND
        /* Subtitles, Interface */
        2L, 3L -> TEXT
        /* Other */
        else -> TEXT
    }

    val locale = requireFieldInitialized(
        "language_supports.language.locale",
        language?.locale ?: "",
    )

    return getLanguageCodeFromLocale(locale)?.let { languageSupportType to it }
}

private fun getLanguageCodeFromLocale(locale: String): LanguageCode? {
    val threeLetterCode = try {
        Locale.forLanguageTag(locale).getISO3Language()
    } catch (@Suppress("SwallowedException") missingResource: MissingResourceException) {
        null
    }
    return LanguageCode.fromOrNull(threeLetterCode)
}

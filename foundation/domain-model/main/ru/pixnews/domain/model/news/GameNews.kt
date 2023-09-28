/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.news

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

public data class GameNews(
    val id: GameNewsId,
    val title: String,
    val author: NewsAuthor,
    val language: LanguageCode,
    val summary: RichText,
    val date: Instant?,

    val text: RichText,
    val source: NewsDataSource,
    val tags: ImmutableSet<String>,
)

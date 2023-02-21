/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.domain.model.news

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.util.RichText

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

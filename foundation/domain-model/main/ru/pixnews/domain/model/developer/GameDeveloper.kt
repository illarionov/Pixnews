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
package ru.pixnews.domain.model.developer

import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.datasource.DataSources
import ru.pixnews.domain.model.developer.DeveloperStatus.UNKNOWN
import ru.pixnews.domain.model.links.ExternalLinks
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

public data class GameDeveloper(
    val id: GameDeveloperId,
    val name: String,
    val avatar: ImageUrl?,
    val url: Url?,
    val foundingDate: LocalDate?,
    val status: DeveloperStatus = UNKNOWN,

    val dataSources: DataSources,
    val links: ExternalLinks<GameDeveloperId>,

    val description: Localized<RichText>,
)

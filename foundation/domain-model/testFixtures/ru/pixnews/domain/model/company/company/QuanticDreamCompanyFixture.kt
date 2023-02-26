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
package ru.pixnews.domain.model.company.company

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.CompanyId
import ru.pixnews.domain.model.company.CompanyStatus.ACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.locale.CountryCode
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val quanticDreamCompanyId = CompanyId("quantic-dream")

public val CompanyFixtures.quanticDream: Company
    get() = Company(
        id = quanticDreamCompanyId,
        name = "Quantic Dream",
        description = Localized(
            value = RichText(
                """Creating experiences based on emotion is our passion. We tell
                |stories in which the players are the heroes: that surprise them, encourage them to consider their feelings
                |and values, and to make them face the consequences of their choices. We create experiences that
                |meaningfully consider personal and social issues. And we find new creative and technical solutions,
                |to challenge ourselves with every game, to explore, invent, create and innovate. This is the DNA of our
                |studio.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4xg.png",
            size = CanvasSize(284U, 71U),
        ),
        foundingDate = ApproximateDate.Year(1997),
        status = ACTIVE,
        country = CountryCode("FR"),
        parentCompany = CompanyId("netease-games"),
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.quanticdream.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/Quantic_Dream")),
        ),
    )

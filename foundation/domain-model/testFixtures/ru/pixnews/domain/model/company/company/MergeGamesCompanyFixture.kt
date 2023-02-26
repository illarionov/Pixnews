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
import kotlinx.datetime.Month
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
import ru.pixnews.domain.model.util.ApproximateDate.YearMonthDay
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val mergeGamesCompanyId = CompanyId("merge-games")

public val CompanyFixtures.mergeGames: Company
    get() = Company(
        id = mergeGamesCompanyId,
        name = "Merge Games",
        description = Localized(
            value = RichText(
                """Merge Games Ltd, based in England, is a developer and publisher of independent
                |games for the retail and digital marketplace.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/rhsdgruo3pyrywvdxrzr.png",
            size = CanvasSize(263U, 160U),
        ),
        foundingDate = YearMonthDay(year = 2010, month = Month.JANUARY, dayOfMonth = 1),
        status = ACTIVE,
        country = CountryCode("UK"),
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.mergegames.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/MergeGamesLtd")),
        ),
    )

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
import ru.pixnews.domain.model.util.ApproximateDate.YearMonth
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val ioInteractiveCompanyId = CompanyId("io-interactive")

public val CompanyFixtures.ioInteractive: Company
    get() = Company(
        id = ioInteractiveCompanyId,
        name = "IO Interactive",
        description = Localized(
            value = RichText(
                """IO Interactive was founded in September 1998 in Copenhagen, Denmark by the
                | Reto-Moto development group. To date, their most popular franchise is the critically acclaimed
                | Hitman series. IO Interactive remained a subsidiary of Square Enix until 2017, when its parent
                | started seeking buyers for the studio. IO Interactive completed a management buyout, regaining their
                | independent status and retaining the rights for Hitman and Freedom Fighters, in June 2017.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4xv.png",
            size = CanvasSize(284U, 45U),
        ),
        foundingDate = YearMonth(year = 2018, month = Month.SEPTEMBER),
        status = ACTIVE,
        country = CountryCode("DK"),
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.ioi.dk/")),
            ExternalLink(TWITTER, Url("https://twitter.com/iointeractive")),
        ),
    )
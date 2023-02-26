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
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val electronicArtsCompanyId = CompanyId("electronic-arts")

public val CompanyFixtures.electronicArts: Company
    get() = Company(
        id = electronicArtsCompanyId,
        name = "Electronic Arts",
        description = Localized(
            value = RichText(
                """Electronic Arts is a leading global interactive entertainment software company.
                |EA delivers games, content and online services for Internet-connected consoles, personal computers,
                | mobile phones and tablets.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4c4.png"),
        foundingDate = ApproximateDate.YearMonthDay(1982, Month.MAY, 28),
        status = ACTIVE,
        country = CountryCodeFixtures.us,
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.ea.com/")),
        ),
    )

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
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val ubisoftEntertainmentCompanyId = CompanyId("ubisoft-entertainment")

public val CompanyFixtures.ubisoftEntertainment: Company
    get() = Company(
        id = ubisoftEntertainmentCompanyId,
        name = "Ubisoft Entertainment",
        description = Localized(
            value = RichText(
                """At Ubisoft, we create worlds for everyone. We are dedicated to enriching the lives
                | of our players by developing high-quality games that resonate with all kinds of personalities,
                | bring people together, and allow everyone to learn and grow while having fun.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4xr.png"),
        foundingDate = ApproximateDate.YearMonthDay(1986, Month.MARCH, 28),
        status = ACTIVE,
        country = null,
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://www.ubisoft.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/Ubisoft")),
        ),
    )

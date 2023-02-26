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
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val ubisoftMontpellierCompanyId = CompanyId("ubisoft-montpellier")

public val CompanyFixtures.ubisoftMontpellier: Company
    get() = Company(
        id = ubisoftMontpellierCompanyId,
        name = "Ubisoft Montpellier",
        description = Localized(
            value = RichText(
                """Ubisoft Montpellier, formerly Ubi Pictures, was founded in 1994 as one of Ubisoft's
                |graphics departments in Castelnau-le-Lez, France. The studio has released several successful
                |games under Michel Ancel's direction, including Rayman and Beyond Good & Evil. The company also worked
                |on several licensed titles, including The Adventures of Tintin: The Secret of the Unicorn and Peter
                |Jackson's King Kong, and collaborated with Eric Chahi on From Dust. The studio also developed the
                |UbiArt Framework engine, used in titles like Rayman Origins, Rayman Legends and Valiant Hearts:
                |The Great War.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4m6.png"),
        foundingDate = ApproximateDate.Year(1994),
        status = ACTIVE,
        country = CountryCode("FR"),
        parentCompany = CompanyFixtures.ubisoftEntertainment.id,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://www.ubisoft.com/en-US/studio/montpellier.aspx")),
            ExternalLink(TWITTER, Url("https://twitter.com/ubisoft")),
        ),
    )

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.company.company

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.CompanyStatus.ACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.country.CountryCode
import ru.pixnews.library.internationalization.language.LanguageCode

private val ubisoftMontpellierCompanyId = DefaultCompanyId("ubisoft-montpellier")

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
        parentCompany = Ref.Id(CompanyFixtures.ubisoftEntertainment.id),
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://www.ubisoft.com/en-US/studio/montpellier.aspx")),
            ExternalLink(TWITTER, Url("https://twitter.com/ubisoft")),
        ),
    )

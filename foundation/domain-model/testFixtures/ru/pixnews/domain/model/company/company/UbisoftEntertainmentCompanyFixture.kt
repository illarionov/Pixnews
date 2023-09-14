/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.company.company

import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Month
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.CompanyStatus.ACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.RichText

private val ubisoftEntertainmentCompanyId = DefaultCompanyId("ubisoft-entertainment")

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

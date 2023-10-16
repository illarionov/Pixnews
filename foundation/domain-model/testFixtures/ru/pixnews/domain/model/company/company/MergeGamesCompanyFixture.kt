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
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.country.CountryCode
import ru.pixnews.library.internationalization.language.LanguageCode

private val mergeGamesCompanyId = DefaultCompanyId("merge-games")

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

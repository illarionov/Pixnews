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
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate.YearMonth
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.country.CountryCode
import ru.pixnews.library.internationalization.language.LanguageCode

private val ioInteractiveCompanyId = DefaultCompanyId("io-interactive")

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

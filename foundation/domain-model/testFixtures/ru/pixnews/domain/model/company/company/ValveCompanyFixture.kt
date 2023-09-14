/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.company.company

import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.CompanyStatus.ACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate.YearMonthDay
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.RichText

private val valveCompanyId = DefaultCompanyId(stringValue = "valve")

public val CompanyFixtures.vale: Company
    get() = Company(
        id = valveCompanyId,
        name = "Valve",
        description = Localized(
            value = RichText(
                """
                    |Valve is an American video game developer, publisher and digital distribution company.
                    |It is the developer of the software distribution platform Steam and the franchises
                    |Half-Life, Counter-Strike, Portal, Day of Defeat, Team Fortress, Left 4 Dead and Dota.
                    |The company was founded by former Microsoft employees Gabe Newell and Mike Harrington."""
                    .trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl2he.png",
            size = CanvasSize(width = 284U, height = 79U),
        ),
        foundingDate = YearMonthDay(LocalDate(1996, 8, 24)),
        status = ACTIVE,
        country = CountryCodeFixtures.us,
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://www.valvesoftware.com/")),
        ),
    )

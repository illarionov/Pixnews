/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val hypixelStudiosCompanyId = CompanyId("hypixel-studios")

public val CompanyFixtures.hypixelStudios: Company
    get() = Company(
        id = hypixelStudiosCompanyId,
        name = "Hypixel Studios",
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl2n9.png",
            size = CanvasSize(284U, 147U),
        ),
        foundingDate = ApproximateDate.YearMonthDay(year = 2018, month = Month.DECEMBER, dayOfMonth = 13),
        status = ACTIVE,
        country = null,
        parentCompany = CompanyFixtures.riotGames.id,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://hypixelstudios.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/Hypixel")),
        ),
    )

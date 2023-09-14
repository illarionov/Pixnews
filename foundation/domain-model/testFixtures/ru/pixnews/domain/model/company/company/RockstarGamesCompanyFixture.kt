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
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.url.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText

private val rockstarGamesCompanyId = DefaultCompanyId("rockstar-games")

public val CompanyFixtures.rockstarGames: Company
    get() = Company(
        id = rockstarGamesCompanyId,
        name = "Rockstar Games",
        description = Localized(
            value = RichText(""),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/wcqgg7udjjtzxjsqfnfi.png",
            size = CanvasSize(width = 174U, height = 160U),
        ),
        foundingDate = ApproximateDate.Year(1998),
        status = ACTIVE,
        country = CountryCodeFixtures.us,
        parentCompany = Ref.Id(DefaultCompanyId("take-two-interactive")),
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(
                type = OFFICIAL,
                url = Url("https://www.rockstargames.com/"),
                language = LanguageCode.ENGLISH,
            ),
            ExternalLink(
                type = OFFICIAL,
                url = Url("https://www.rockstargames.com/zh/"),
                language = LanguageCode.CHINESE,
            ),
            ExternalLink(
                type = OFFICIAL,
                url = Url("https://www.rockstargames.com/br/"),
                language = LanguageCode.PORTUGUESE,
            ),
            ExternalLink(
                type = OFFICIAL,
                url = Url("https://www.rockstargames.com/ru/"),
                language = LanguageCode.RUSSIAN,
            ),
            ExternalLink(
                type = WIKIPEDIA,
                url = Url("https://en.wikipedia.org/wiki/Rockstar_Games"),
                language = LanguageCode.ENGLISH,
            ),
            ExternalLink(
                WIKIPEDIA,
                Url("https://ru.wikipedia.org/wiki/Rockstar_Games"),
                language = LanguageCode.RUSSIAN,
            ),
            ExternalLink(TWITCH, Url("https://www.twitch.tv/rockstargames")),
            ExternalLink(TWITTER, Url("https://twitter.com/rockstargames")),
            ExternalLink(YOUTUBE, Url("https://www.youtube.com/@RockstarGames")),
            ExternalLink(FACEBOOK, Url("https://www.facebook.com/rockstargames")),
        ),
    )

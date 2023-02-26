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
import ru.pixnews.domain.model.links.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.links.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.links.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.links.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val rockstarGamesCompanyId = CompanyId("rockstar-games")

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
        parentCompany = CompanyId("take-two-interactive"),
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

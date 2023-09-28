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
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

private val greatApeGamesCompanyId = DefaultCompanyId("great-ape-games")

public val CompanyFixtures.greatApeGames: Company
    get() = Company(
        id = greatApeGamesCompanyId,
        name = "Great Ape Games",
        description = Localized(
            value = RichText(
                """From the site: “Great Ape Games is a tribe of humans amalgamated together by a shared
                |passion for creating exceptional games. We're a tight-knit indie team with tons of ambition, based
                |in the vibrant seaside town of Brighton”.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl3ii.png"),
        foundingDate = null,
        status = ACTIVE,
        country = null,
        parentCompany = null,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://greatapegames.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/greatapegames?s=21")),
        ),
    )

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
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.country.CountryCode
import ru.pixnews.library.internationalization.language.LanguageCode

private val quanticDreamCompanyId = DefaultCompanyId("quantic-dream")

public val CompanyFixtures.quanticDream: Company
    get() = Company(
        id = quanticDreamCompanyId,
        name = "Quantic Dream",
        description = Localized(
            value = RichText(
                """Creating experiences based on emotion is our passion. We tell
                |stories in which the players are the heroes: that surprise them, encourage them to consider their feelings
                |and values, and to make them face the consequences of their choices. We create experiences that
                |meaningfully consider personal and social issues. And we find new creative and technical solutions,
                |to challenge ourselves with every game, to explore, invent, create and innovate. This is the DNA of our
                |studio.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(
            rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4xg.png",
            size = CanvasSize(284U, 71U),
        ),
        foundingDate = Date.Year(1997),
        status = ACTIVE,
        country = CountryCode("FR"),
        parentCompany = Ref.Id(DefaultCompanyId("netease-games")),
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.quanticdream.com/")),
            ExternalLink(TWITTER, Url("https://twitter.com/Quantic_Dream")),
        ),
    )

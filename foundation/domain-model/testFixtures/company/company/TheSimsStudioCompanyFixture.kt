/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.company.company

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.CompanyStatus.INACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.id.DefaultCompanyId
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

private val theSimsStudioCompanyId = DefaultCompanyId("the-sims-studio")

public val CompanyFixtures.theSimsStudio: Company
    get() = Company(
        id = theSimsStudioCompanyId,
        name = "The Sims Studio",
        description = Localized(
            value = RichText(
                """The Sims Studio was a subsidiary of Electronic Arts located primarily at EA's
                | Redwood City campus. It took over development of The Sims series from Maxis (which allowed Maxis to
                |devote its resources to developing Spore). The Sims Studio has developed all games in The Sims
                |series since The Sims 2: Bon Voyage, including The Sims 3 and all of its expansion and stuff packs.
                |Despite The Sims development leaving Maxis, many Maxis developers remained with The Sims
                |Division/The Sims Studio to continue developing the series.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        avatar = DefaultImageUrl(rawUrl = "https://images.igdb.com/igdb/image/upload/t_logo_med/cl4c6.png"),
        foundingDate = Date.Year(2006),
        status = INACTIVE,
        country = CountryCodeFixtures.us,
        parentCompany = Ref.Id(CompanyFixtures.electronicArts.id),
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(),
    )

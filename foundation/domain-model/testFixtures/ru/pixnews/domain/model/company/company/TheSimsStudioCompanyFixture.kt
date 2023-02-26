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
import ru.pixnews.domain.model.company.CompanyStatus.INACTIVE
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.locale.CountryCodeFixtures
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.us
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText

private val theSimsStudioCompanyId = CompanyId("the-sims-studio")

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
        foundingDate = ApproximateDate.Year(2006),
        status = INACTIVE,
        country = CountryCodeFixtures.us,
        parentCompany = CompanyFixtures.electronicArts.id,
        dataSources = DataSourceFixtures.igdb,
        links = persistentListOf(),
    )

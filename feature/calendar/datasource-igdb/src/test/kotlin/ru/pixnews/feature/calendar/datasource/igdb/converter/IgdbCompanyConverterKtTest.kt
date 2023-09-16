/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.domain.model.id.CompanyId
import ru.pixnews.domain.model.locale.CountryCode
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCompanyFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.company.larianStudios
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.company.wushuStudios
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbCompanyId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.feature.calendar.datasource.igdb.model.url.IgdbImageUrl
import ru.pixnews.igdbclient.model.Company as IgdbCompany

class IgdbCompanyConverterKtTest {
    @ParameterizedTest
    @MethodSource("companyTestSource")
    fun `toCompanyRef() should convert companies`(testData: Pair<IgdbCompany, Ref<Company>>) {
        val result = testData.first.toCompanyRef()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toCompanyRef() should fail when required fields are not requested`() {
        shouldThrow<IllegalArgumentException> {
            IgdbCompany(id = 0, name = "Larian Studios").toCompanyRef()
        }
    }

    companion object {
        @JvmStatic
        @Suppress("LongMethod")
        fun companyTestSource(): List<Pair<IgdbCompany, Ref<Company>>> = listOf(
            IgdbCompanyFixtures.larianStudios to FullObject(
                Company(
                    id = IgdbCompanyId(id = 510),
                    name = "Larian Studios",
                    description = Localized(
                        RichText(
                            "Larian Studios is a game developer studio located in Gent, " +
                                    "Belgium. Proud to be independent for 19 years, they create games " +
                                    "for multiple genres and platforms.\\n" +
                                    "They have a passion for role playing games; our Divinity series " +
                                    "includes 5 games spanning over 13 years in multiple genres. " +
                                    "Their crown jewel, Divinity: Original Sin, was a Kickstarter " +
                                    "success story of 2014.",
                        ),
                        LanguageCode.ENGLISH,
                    ),
                    avatar = IgdbImageUrl(
                        igdbImageId = "emebqrowinirmlk4r69n",
                        animated = false,
                        size = CanvasSize(1734U, 2686U),
                    ),
                    foundingDate = ApproximateDate.Year(year = 1996),
                    status = UNKNOWN,
                    country = CountryCode("BE"),
                    parentCompany = null,
                    dataSources = persistentListOf(igdbDataSource),
                    links = persistentListOf(
                        ExternalLink(OFFICIAL, Url("http://larian.com/")),
                    ),
                ),
            ),
            IgdbCompanyFixtures.wushuStudios to FullObject(
                Company(
                    id = IgdbCompanyId(id = 47197),
                    name = "Wushu Studios",
                    description = Localized(
                        RichText(
                            "Founded in 2017 by a group of industry veterans, " +
                                    "Wushu are an experienced and professional team of creative " +
                                    "and technical developers based in the heart of Liverpool’s " +
                                    "digital creative hub, The Baltic Triangle.",
                        ),
                        LanguageCode.ENGLISH,
                    ),
                    avatar = IgdbImageUrl(
                        igdbImageId = "cl6qd",
                        animated = false,
                        size = CanvasSize(472U, 128U),
                    ),
                    foundingDate = ApproximateDate.Year(year = 2017),
                    status = UNKNOWN,
                    country = CountryCode("GB"),
                    parentCompany = null,
                    dataSources = persistentListOf(igdbDataSource),
                    links = persistentListOf(
                        ExternalLink(OFFICIAL, Url("https://wushustudios.com/")),
                    ),
                ),
            ),
            IgdbCompany(id = 510) to Ref.Id<Company, CompanyId>(IgdbCompanyId(id = 510)),
        )
    }
}

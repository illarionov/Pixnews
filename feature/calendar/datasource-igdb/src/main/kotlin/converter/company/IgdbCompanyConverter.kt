/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.company

import at.released.igdbclient.dsl.field.CompanyFieldDsl
import at.released.igdbclient.dsl.field.CompanyWebsiteFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.CompanyWebsite
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import com.squareup.wire.Instant
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter
import ru.pixnews.domain.model.id.CompanyId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.Ref.Id
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.feature.calendar.datasource.igdb.converter.toExternalLinkType
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.asLocalDate
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbCompanyId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.library.internationalization.country.CountryCode
import ru.pixnews.library.internationalization.country.fromNumeric3Code
import ru.pixnews.library.internationalization.language.LanguageCode

internal object IgdbCompanyConverter {
    fun getRequiredFields(from: CompanyFieldDsl): List<IgdbRequestField<*>> = with(from) {
        listOf(
            id,
            name,
            description,
            start_date,
            start_date_category,
            country,
            parent.id,
            url,
        ) + IgdbCompanyLogoConverter.getRequiredFields(logo) +
                websites.getCompanyWebsiteRequiredFields()
    }

    internal fun convertToCompanyRef(company: at.released.igdbclient.model.Company): Ref<Company> = when {
        company.name.isNotEmpty() -> FullObject(convert(company))
        company.id != 0L -> Id(getIgdbCompanyId(company.id))
        else -> errorFieldNotRequested("company.id")
    }

    fun convert(igdbObject: at.released.igdbclient.model.Company): Company = with(igdbObject) {
        val id = getIgdbCompanyId(requireFieldInitialized("company.id", id))
        val name = requireFieldInitialized("company.name", name)

        return Company(
            id = id,
            name = name,
            description = Localized(RichText(this.description), LanguageCode.ENGLISH),
            avatar = logo?.let { IgdbCompanyLogoConverter.convert(it) },
            foundingDate = start_date?.let { parseIgdbStartDate(it, start_date_category) },
            status = UNKNOWN,
            country = if (country != 0) CountryCode.fromNumeric3Code(country) else null,
            parentCompany = parent?.let { convertToCompanyRef(it) },
            dataSources = persistentListOf(igdbDataSource),
            links = websites.asSequence().map { it.toExternalLink() }.toImmutableList(),
        )
    }

    private fun CompanyWebsiteFieldDsl.getCompanyWebsiteRequiredFields(): List<IgdbRequestField<*>> = listOf(
        category,
        url,
    )

    private fun getIgdbCompanyId(id: Long): CompanyId = IgdbCompanyId(id)

    private fun CompanyWebsite.toExternalLink(): ExternalLink = ExternalLink(
        type = this.category.toExternalLinkType() ?: errorFieldNotRequested("websites.category"),
        url = Url(requireFieldInitialized("websites.url", url)),
    )

    @Suppress("MagicNumber")
    private fun parseIgdbStartDate(
        date: Instant,
        changeDateCategory: DateFormatChangeDateCategoryEnum,
    ): Date = when (changeDateCategory) {
        YYYYMMMMDD -> YearMonthDay(date.asLocalDate)
        YYYYMMMM -> YearMonth(date.asLocalDate)
        YYYY -> Year(date.asLocalDate.year)
        YYYYQ1 -> YearQuarter(date.asLocalDate.year, 1)
        YYYYQ2 -> YearQuarter(date.asLocalDate.year, 2)
        YYYYQ3 -> YearQuarter(date.asLocalDate.year, 3)
        YYYYQ4 -> YearQuarter(date.asLocalDate.year, 4)
        TBD -> Unknown()
    }
}

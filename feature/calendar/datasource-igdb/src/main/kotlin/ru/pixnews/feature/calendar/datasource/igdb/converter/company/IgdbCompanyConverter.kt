/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.company

import com.squareup.wire.Instant
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.domain.model.id.CompanyId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ApproximateDate.Quarter
import ru.pixnews.domain.model.util.ApproximateDate.Unknown
import ru.pixnews.domain.model.util.ApproximateDate.Year
import ru.pixnews.domain.model.util.ApproximateDate.YearMonth
import ru.pixnews.domain.model.util.ApproximateDate.YearMonthDay
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.Ref.Id
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.feature.calendar.datasource.igdb.converter.toExternalLinkType
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbCompanyId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.dsl.field.CompanyFieldDsl
import ru.pixnews.igdbclient.dsl.field.CompanyWebsiteFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.CompanyWebsite
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
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

    internal fun convertToCompanyRef(company: ru.pixnews.igdbclient.model.Company): Ref<Company> = when {
        company.name.isNotEmpty() -> FullObject(convert(company))
        company.id != 0L -> Id(getIgdbCompanyId(company.id))
        else -> errorFieldNotRequested("company.id")
    }

    fun convert(igdbObject: ru.pixnews.igdbclient.model.Company): Company = with(igdbObject) {
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
    ): ApproximateDate {
        val instant = date.toKotlinInstant()
        return when (changeDateCategory) {
            YYYYMMMMDD -> YearMonthDay(instant.toLocalDateTime(TimeZone.UTC).date)
            YYYYMMMM -> YearMonth(instant.toLocalDateTime(TimeZone.UTC).date)
            YYYY -> Year(instant.toLocalDateTime(TimeZone.UTC).date.year)
            YYYYQ1 -> Quarter(1)
            YYYYQ2 -> Quarter(2)
            YYYYQ3 -> Quarter(3)
            YYYYQ4 -> Quarter(4)
            TBD -> Unknown()
        }
    }
}

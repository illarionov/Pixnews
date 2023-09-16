/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.domain.model.id.CompanyId
import ru.pixnews.domain.model.locale.CountryCode
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.fromNumeric3Code
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbCompanyId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
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
import com.squareup.wire.Instant as WireInstant
import ru.pixnews.igdbclient.model.Company as IgdbCompany

internal fun IgdbCompany.toCompanyRef(): Ref<Company> = when {
    name.isNotEmpty() -> Ref.FullObject(this.toCompany())
    id != 0L -> Ref.Id(getIgdbCompanyId(id))
    else -> errorFieldNotRequested("company.id")
}

internal fun IgdbCompany.toCompany(): Company {
    val id = getIgdbCompanyId(requireFieldInitialized("company.id", id))
    val name = requireFieldInitialized("company.name", name)

    return Company(
        id = id,
        name = name,
        description = Localized(RichText(this.description), LanguageCode.ENGLISH),
        avatar = logo?.toIgdbImageUrl(),
        foundingDate = start_date?.let { parseIgdbStartDate(it, start_date_category) },
        status = UNKNOWN,
        country = if (country != 0) CountryCode.fromNumeric3Code(country) else null,
        parentCompany = parent?.toCompanyRef(),
        dataSources = persistentListOf(igdbDataSource),
        links = websites.asSequence().map(CompanyWebsite::toExternalLink).toImmutableList(),
    )
}

private fun getIgdbCompanyId(id: Long): CompanyId = IgdbCompanyId(id)

private fun CompanyWebsite.toExternalLink(): ExternalLink = ExternalLink(
    type = this.category.toExternalLinkType() ?: errorFieldNotRequested("websites.category"),
    url = Url(requireFieldInitialized("websites.url", url)),
)

@Suppress("MagicNumber")
internal fun parseIgdbStartDate(
    date: WireInstant,
    changeDateCategory: DateFormatChangeDateCategoryEnum,
): ApproximateDate {
    val instant = date.toKotlinInstant()
    return when (changeDateCategory) {
        YYYYMMMMDD -> ApproximateDate.YearMonthDay(instant.toLocalDateTime(TimeZone.UTC).date)
        YYYYMMMM -> ApproximateDate.YearMonth(instant.toLocalDateTime(TimeZone.UTC).date)
        YYYY -> ApproximateDate.Year(instant.toLocalDateTime(TimeZone.UTC).date.year)
        YYYYQ1 -> ApproximateDate.Quarter(1)
        YYYYQ2 -> ApproximateDate.Quarter(2)
        YYYYQ3 -> ApproximateDate.Quarter(3)
        YYYYQ4 -> ApproximateDate.Quarter(4)
        TBD -> ApproximateDate.Unknown()
    }
}

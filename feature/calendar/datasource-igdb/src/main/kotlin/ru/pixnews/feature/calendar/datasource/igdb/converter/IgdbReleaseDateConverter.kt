/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import ru.pixnews.domain.model.locale.LanguageCode.Companion.ENGLISH
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import ru.pixnews.igdbclient.model.ReleaseDate as IgdbReleaseDate

internal fun IgdbReleaseDate.toApproximateDate(): ApproximateDate = when (this.category) {
    YYYYMMMMDD -> ApproximateDate.YearMonthDay(
        date = requireNotNull(this.date) { "release_date.date field should be set" }
            .toKotlinInstant()
            .toLocalDateTime(TimeZone.UTC)
            .date,
    )

    YYYYMMMM -> ApproximateDate.YearMonth(
        year = this.y,
        month = Month(this.m),
    )

    YYYY -> ApproximateDate.Year(year = this.y)
    YYYYQ1 -> ApproximateDate.ToBeDeterminedQuarter(
        year = this.y,
        quarter = 1,
        description = Localized(this.human, ENGLISH),
    )

    YYYYQ2 -> ApproximateDate.ToBeDeterminedQuarter(
        year = this.y,
        quarter = 2,
        description = Localized(this.human, ENGLISH),
    )

    YYYYQ3 -> ApproximateDate.ToBeDeterminedQuarter(
        year = this.y,
        quarter = 3,
        description = Localized(this.human, ENGLISH),
    )

    YYYYQ4 -> ApproximateDate.ToBeDeterminedQuarter(
        year = this.y,
        quarter = 4,
        description = Localized(this.human, ENGLISH),
    )

    TBD -> ApproximateDate.ToBeDetermined(
        expected = null,
        description = Localized(this.human, ENGLISH),
    )
}

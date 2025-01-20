/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import at.released.igdbclient.model.Game
import at.released.igdbclient.model.ReleaseDate
import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.asLocalDate
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireField
import ru.pixnews.library.internationalization.language.LanguageCode

internal object IgdbGameReleaseDateConverter : IgdbGameFieldConverter<Date> {
    private val ReleaseDate.dateAsLocalDate: LocalDate
        get() = requireNotNull(this.date) { "release_date.date field should be set" }.asLocalDate

    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> {
        return listOf(
            from.release_dates.id,
            from.release_dates.date,
            from.release_dates.updated_at,
            from.release_dates.category,
            from.release_dates.human,
        )
    }

    override fun convert(game: Game): Date = game.findMostRelevantReleaseDate()
        ?.let(::convertIgdbReleaseDateToApproximateDate)
        ?: Unknown()

    private fun Game.findMostRelevantReleaseDate(): ReleaseDate? {
        release_dates.forEach { requireField("game.updated_at", it.updated_at != null) }
        return release_dates.maxWithOrNull(
            compareBy(ReleaseDate::updated_at, ReleaseDate::date, ReleaseDate::id),
        )
    }

    private fun convertIgdbReleaseDateToApproximateDate(igdbReleaseDate: ReleaseDate): Date =
        with(igdbReleaseDate) {
            when (this.category) {
                YYYYMMMMDD -> Date.YearMonthDay(dateAsLocalDate)
                YYYYMMMM -> Date.YearMonth(dateAsLocalDate)
                YYYY -> Date.Year(dateAsLocalDate.year)
                YYYYQ1 -> Date.YearQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 1,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ2 -> Date.YearQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 2,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ3 -> Date.YearQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 3,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ4 -> Date.YearQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 4,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                TBD -> Date.Unknown(
                    expected = null,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )
            }
        }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ApproximateDate.Unknown
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.asLocalDate
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireField
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.ReleaseDate
import ru.pixnews.library.internationalization.language.LanguageCode

internal object IgdbGameReleaseDateConverter : IgdbGameFieldConverter<ApproximateDate> {
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

    override fun convert(game: Game): ApproximateDate = game.findMostRelevantReleaseDate()
        ?.let(::convertIgdbReleaseDateToApproximateDate)
        ?: Unknown()

    private fun Game.findMostRelevantReleaseDate(): ReleaseDate? {
        release_dates.forEach { requireField("game.updated_at", it.updated_at != null) }
        return release_dates.maxWithOrNull(
            compareBy(ReleaseDate::updated_at, ReleaseDate::date, ReleaseDate::id),
        )
    }

    private fun convertIgdbReleaseDateToApproximateDate(igdbReleaseDate: ReleaseDate): ApproximateDate =
        with(igdbReleaseDate) {
            when (this.category) {
                YYYYMMMMDD -> ApproximateDate.YearMonthDay(dateAsLocalDate)
                YYYYMMMM -> ApproximateDate.YearMonth(dateAsLocalDate)
                YYYY -> ApproximateDate.Year(dateAsLocalDate.year)
                YYYYQ1 -> ApproximateDate.ToBeDeterminedQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 1,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ2 -> ApproximateDate.ToBeDeterminedQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 2,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ3 -> ApproximateDate.ToBeDeterminedQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 3,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                YYYYQ4 -> ApproximateDate.ToBeDeterminedQuarter(
                    year = dateAsLocalDate.year,
                    quarter = 4,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )

                TBD -> ApproximateDate.ToBeDetermined(
                    expected = null,
                    description = Localized(this.human, LanguageCode.ENGLISH),
                )
            }
        }
}

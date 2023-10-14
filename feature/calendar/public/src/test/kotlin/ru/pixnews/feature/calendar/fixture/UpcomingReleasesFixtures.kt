/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.fixture

import arrow.atomic.AtomicInt
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDetermined
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.TBD
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentYear
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextYear
import ru.pixnews.library.internationalization.language.LanguageCode
import kotlin.reflect.KProperty

object UpcomingReleasesFixtures {
    val baseGame = GameFixtures.halfLife3

    // Few days
    object ReleasesInNextFewDays {
        val releasedAtStartOfWeek by upcomingRelease(CurrentMonth.exactDateStartOfWeek, FEW_DAYS)
        val releasedYesterday by upcomingRelease(CurrentMonth.exactDateYesterday, FEW_DAYS)
        val releasedToday by upcomingRelease(CurrentMonth.exactDateToday, FEW_DAYS)
        val releasedToday2 by upcomingRelease(CurrentMonth.exactDateToday, FEW_DAYS)
        val willBeReleasedTomorrow by upcomingRelease(CurrentMonth.exactDateTomorrow, FEW_DAYS)
    }

    // Current month
    object ReleasesInCurrentMonth {
        val thisMonthDay25 by upcomingRelease(CurrentMonth.exactDateLater25, CURRENT_MONTH)
        val thisMonthDay26 by upcomingRelease(CurrentMonth.exactDateLater26, CURRENT_MONTH)
        val tbdThisMonth1 by upcomingRelease(CurrentMonth.approxDate, CURRENT_MONTH)
        val tbdThisMonth2 by upcomingRelease(CurrentMonth.approxDate, CURRENT_MONTH)
    }

    // Next month
    object ReleasesInNextMonth {
        val nextMonthDay10 by upcomingRelease(NextMonth.exactDate10, NEXT_MONTH)
        val nextMonthDay15 by upcomingRelease(NextMonth.exactDate15, NEXT_MONTH)
        val tbdNextMonth1 by upcomingRelease(NextMonth.approxDate, NEXT_MONTH)
        val tbdNextMonth2 by upcomingRelease(NextMonth.approxDate, NEXT_MONTH)
    }

    object ReleasesInThisQuarter {
        val thisQuarterExactDate by upcomingRelease(CurrentQuarter.exactDate, CURRENT_QUARTER)
        val tbdThisQuarterAug by upcomingRelease(CurrentQuarter.approxDateMonth, CURRENT_QUARTER)
        val tbdThisQuarter by upcomingRelease(CurrentQuarter.approxDateQuarter, CURRENT_QUARTER)
    }

    object ReleasesInNextQuarter {
        val nextQuarterExactDate by upcomingRelease(NextQuarter.exactDate, NEXT_QUARTER)
        val tbdNextQuarterOct by upcomingRelease(NextQuarter.approxDateMonth, NEXT_QUARTER)
        val tbdNextQuarter by upcomingRelease(NextQuarter.approxDate3Quarter, NEXT_QUARTER)
    }

    object ReleasesThisYear {
        val thisYearExactDate by upcomingRelease(CurrentYear.exactDate2Oct, CURRENT_YEAR)
        val tbdThisYearOct by upcomingRelease(CurrentYear.approxDateOctober, CURRENT_YEAR)
        val tbdThisYear4Quarter by upcomingRelease(CurrentYear.approxDate4Quarter, CURRENT_YEAR)
        val tbdThisYear by upcomingRelease(CurrentYear.approxDateYear, CURRENT_YEAR)
    }

    // Next Year
    object ReleasesNextYear {
        val nextYearExactDate by upcomingRelease(NextYear.exactDate1jun, NEXT_YEAR)
        val tbdNextYearApril by upcomingRelease(NextYear.approxDateApril, NEXT_YEAR)
        val tbdNextYear1Quarter by upcomingRelease(NextYear.approxDateQuarter, NEXT_YEAR)
        val tbdNextYear by upcomingRelease(NextYear.approxDateYear, NEXT_YEAR)
    }

    object ReleasesTbd {
        val tdbGame1 by upcomingRelease(ToBeDetermined(null), TBD)
        val tbdGame2 by upcomingRelease(ToBeDetermined(null), TBD)
    }

    private fun upcomingRelease(
        releaseDate: ApproximateDate,
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseFixture = UpcomingReleaseFixture(releaseDate, category)

    private class UpcomingReleaseFixture(
        private val releaseDate: ApproximateDate,
        private val category: UpcomingReleaseTimeCategory,
    ) {
        private var release: UpcomingRelease? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): UpcomingRelease {
            return release ?: run {
                val name = "${property.name}-${uniqueTestId.incrementAndGet()}"
                UpcomingRelease(
                    game = baseGame.copy(
                        id = GameId(name),
                        name = Localized(name, LanguageCode.ENGLISH),
                        releaseDate = releaseDate,
                    ),
                    group = category,
                ).also {
                    release = it
                }
            }
        }

        private companion object {
            private val uniqueTestId = AtomicInt(0)
        }
    }
}

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
package ru.pixnews.domain.model.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import ru.pixnews.domain.model.locale.Localized

public sealed class ApproximateDate {
    public data class Unknown(
        val description: Localized<String>? = null,
    ) : ApproximateDate()
    public data class Year(val date: LocalDate)
    public data class YearMonth(val date: LocalDate)
    public data class YearMonthDay(val date: LocalDate)
    public data class ExactDateTime(val date: LocalDateTime)

    public data class ToBeDeterminedYear(
        val year: Int,
        val description: Localized<String>,
    ) : ApproximateDate() {
        init {
            @Suppress("MagicNumber")
            require(year > 1958)
        }
    }

    public data class ToBeDeterminedQuarter(
        val quarter: Int,
        val description: Localized<String>,
    ) : ApproximateDate() {
        init {
            @Suppress("MagicNumber")
            require(quarter in 1..4)
        }
    }

    public data class ToBeDetermined(
        val expected: Pair<Instant, Instant>?,
        val description: Localized<String>,
    )
}

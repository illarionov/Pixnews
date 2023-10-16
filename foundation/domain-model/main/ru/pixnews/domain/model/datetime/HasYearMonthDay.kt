/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

public interface HasYearMonthDay : HasYearMonth {
    public override val year: Int
    public override val month: Month
    public val dayOfMonth: Int
}

public val HasYearMonthDay.localDate: LocalDate
    get() = LocalDate(year, month, dayOfMonth)

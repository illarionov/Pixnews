/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.datetime

import kotlinx.datetime.Month
import ru.pixnews.library.kotlin.datetime.utils.quarter

public interface HasYearMonth : HasYearQuarter {
    public override val year: Int
    public val month: Month

    override val quarter: Int get() = month.quarter
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils.fixtures

import kotlinx.datetime.Instant

public object InstantFixtures {
    public val lastSecondOf5Oct2024: TestInstant = TestInstant(
        isoString = "2024-10-05T23:59:59Z",
        unixEpoch = 1_728_172_799,
    )

    public object LeapSecondJun2015 {
        public val lastNonLeapSecondOfJun2015: TestInstant = TestInstant(
            isoString = "2015-06-30T23:59:59Z",
            unixEpoch = 1_435_708_799,
        )
        public val sixtiethSecondOfJun2015: TestInstant = TestInstant(
            isoString = "2015-06-30T23:59:60Z",
            unixEpoch = 1_435_708_800,
        )
        public val firstSecondOfJul2015: TestInstant = TestInstant(
            isoString = "2015-07-01T00:00:00Z",
            unixEpoch = 1_435_708_800,
        )
    }

    public object LeapSecondDec2016 {
        public val lastNonLeapSecondOfDec2016: TestInstant = TestInstant(
            isoString = "2016-12-31T23:59:59Z",
            unixEpoch = 1_483_228_799,
        )
        public val sixtiethSecondOfDec2016: TestInstant = TestInstant(
            isoString = "2016-12-31T23:59:60Z",
            unixEpoch = 1_483_228_800,
        )
        public val firstSecondOfJan2017: TestInstant = TestInstant(
            isoString = "2017-01-01T00:00:00Z",
            unixEpoch = 1_483_228_800,
        )
    }

    /**
     * @property isoString ISO-8601 formatted date string
     * @property unixEpoch Unix timestamp. The number of seconds since 1970-01-01 00:00:00 UTC without taking into
     * account leap seconds.
     *
     * #### Some ways to get the value of [unixEpoch]:
     * * `unixepoch([isoString])` in SQLite.
     * * `date --date='[isoString]' '+%s'` on system with GNU coreutils date
     * * `date -ujf '%FT%TZ' '[isoString]' '+%s'` on MacOS
     * * `date -ud '[isoString]' '+%s'` on Android (Toybox Android)
     */
    public data class TestInstant(
        val isoString: String,
        val unixEpoch: Long,
    ) {
        /**
         * Instant from [isoString]
         * Result of the [Instant.parse]
         */
        val instant: Instant
            get() = Instant.fromEpochSeconds(unixEpoch)
    }
}

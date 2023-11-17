/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils

import kotlinx.datetime.Instant

public fun Instant.truncateToSeconds(): Instant = if (nanosecondsOfSecond != 0) {
    Instant.fromEpochSeconds(this.epochSeconds)
} else {
    this
}

@Suppress("MagicNumber")
public fun Instant.truncateToMilliseconds(): Instant = if (nanosecondsOfSecond % 1_000_000 != 0) {
    Instant.fromEpochMilliseconds(this.toEpochMilliseconds())
} else {
    this
}

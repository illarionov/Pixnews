/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils

import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class InstantExtTest {
    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59.999999Z | 2024-12-31T23:59:59Z",
        "2024-12-31T23:59:59Z        | 2024-12-31T23:59:59Z",
        delimiter = '|',
    )
    fun `truncateToSeconds() should return correct value`(source: String, expected: String) {
        val instant = Instant.parse(source)
        val expectedInstant = Instant.parse(expected)

        val truncated = instant.truncateToSeconds()

        truncated shouldBe expectedInstant
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z        | 2024-12-31T23:59:59Z",
        "2024-12-31T23:59:59.1Z      | 2024-12-31T23:59:59.1Z",
        "2024-12-31T23:59:59.123Z    | 2024-12-31T23:59:59.123Z",
        "2024-12-31T23:59:59.123000Z | 2024-12-31T23:59:59.123Z",
        "2024-12-31T23:59:59.123100Z | 2024-12-31T23:59:59.123Z",
        "2024-12-31T23:59:59.123456Z | 2024-12-31T23:59:59.123Z",
        "2024-12-31T23:59:59.999999Z | 2024-12-31T23:59:59.999Z",
        delimiter = '|',
    )
    fun `truncateToMilliseconds()  should return correct value`(source: String, expected: String) {
        val instant = Instant.parse(source)
        val expectedInstant = Instant.parse(expected)

        val truncated = instant.truncateToMilliseconds()

        truncated shouldBe expectedInstant
    }
}

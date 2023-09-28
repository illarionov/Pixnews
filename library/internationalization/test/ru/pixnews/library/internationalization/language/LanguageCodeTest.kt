/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.internationalization.language

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

class LanguageCodeTest {
    @Test
    fun `toString() should return the originally set value`() {
        val languageCode = LanguageCode.from("deu")

        languageCode.toString() shouldBe "deu"
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
        strings = [
            "  ",
            "a",
            "en",
            "Eng",
            "ENG",
            "Chinese",
        ],
    )
    fun `fromOrNull() should return null on incorrect value`(testCode: String?) {
        val languageCode = LanguageCode.fromOrNull(testCode)
        languageCode shouldBe null
    }

    @Test
    fun `from() should throw exception on incorrect value`() {
        shouldThrow<IllegalArgumentException> {
            LanguageCode.from("123")
        }
    }
}

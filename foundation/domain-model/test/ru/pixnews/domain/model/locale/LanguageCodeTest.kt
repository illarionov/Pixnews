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
package ru.pixnews.domain.model.locale

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

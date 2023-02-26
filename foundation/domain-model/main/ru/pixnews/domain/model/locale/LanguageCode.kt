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

/**
 * ISO 639-3 language code - https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
 */
@JvmInline
@Suppress("MagicNumber")
public value class LanguageCode private constructor(
    private val intValue: UInt,
) {
    override fun toString(): String {
        return intValue.toString(36)
    }

    public companion object {
        public val ARABIC: LanguageCode = from("ara")
        public val BENGALI: LanguageCode = from("ben")
        public val CHINESE: LanguageCode = from("zho")
        public val ENGLISH: LanguageCode = from("eng")
        public val FRENCH: LanguageCode = from("fra")
        public val GERMAN: LanguageCode = from("deu")
        public val HINDI: LanguageCode = from("hin")
        public val INDONESIAN: LanguageCode = from("ind")
        public val ITALIAN: LanguageCode = from("ita")
        public val JAPANESE: LanguageCode = from("jpn")
        public val KOREAN: LanguageCode = from("kor")
        public val POLISH: LanguageCode = from("pol")
        public val PORTUGUESE: LanguageCode = from("por")
        public val RUSSIAN: LanguageCode = from("rus")
        public val SPANISH: LanguageCode = from("spa")
        public val TURKISH: LanguageCode = from("tur")

        public fun from(identifier: String): LanguageCode {
            return requireNotNull(fromOrNull(identifier)) {
                "Incorrect ISO 639-3 code"
            }
        }

        public fun fromOrNull(identifier: String?): LanguageCode? {
            if (identifier == null ||
                    identifier.length != 3 ||
                    !identifier.all { it in 'a'..'z' }
            ) {
                return null
            }
            return identifier.toUIntOrNull(36)?.let(::LanguageCode)
        }
    }
}

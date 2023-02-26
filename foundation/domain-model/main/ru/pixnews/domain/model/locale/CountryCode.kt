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
 * Two-letters ISO 3166-1 country code
 */
@JvmInline
public value class CountryCode(
    public val iso31661Code: String,
) {
    init {
        require(iso31661Code.length == 2)
        require(iso31661Code.all { it in 'A'..'Z' })
    }
    override fun toString(): String = iso31661Code
}

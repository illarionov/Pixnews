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
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableMap
import ru.pixnews.domain.model.locale.LanguageCode

public data class GameSystemRequirements(
    val requirements: ImmutableMap<GamePlatform, PlatformSystemRequirementsByType>,
) {
    init {
        require(requirements.isNotEmpty())
    }
}

public data class PlatformSystemRequirementsByType(
    val minimum: PlatformSystemRequirements,
    val recommended: PlatformSystemRequirements?,
)

public data class PlatformSystemRequirements(
    public val os: String = "",
    public val cpu: String = "",
    public val ram: String = "",
    public val video: String = "",
    public val disk: String = "",
    public val other: String = "",
    val language: LanguageCode = LanguageCode.ENGLISH,
)

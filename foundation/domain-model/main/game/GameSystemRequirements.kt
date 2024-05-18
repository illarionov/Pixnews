/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableMap
import ru.pixnews.library.internationalization.language.LanguageCode

public data class GameSystemRequirements(
    val requirements: ImmutableMap<GamePlatform, PlatformSystemRequirementsByType>,
) {
    init {
        require(requirements.isNotEmpty())
    }
}

public data class PlatformSystemRequirementsByType(
    val minimum: PlatformSystemRequirements?,
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

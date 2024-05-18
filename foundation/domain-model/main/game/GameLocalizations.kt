/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.library.internationalization.language.LanguageCode

public data class GameLocalizations(
    val sound: ImmutableSet<LanguageCode>,
    val text: ImmutableSet<LanguageCode>,
)

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.url

import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.locale.LanguageCode

public data class ExternalLinks<out I>(
    val id: I,
    val links: ImmutableList<ExternalLink>,
)

public data class ExternalLink(
    val type: ExternalLinkType,
    val url: Url,
    val language: LanguageCode? = null,
)

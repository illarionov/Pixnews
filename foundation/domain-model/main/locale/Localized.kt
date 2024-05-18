/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.locale

import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

public data class Localized<out L>(
    public val value: L,
    public val language: LanguageCode,
) {
    public companion object {
        public val EMPTY_STRING: Localized<String> = Localized("", LanguageCode.ENGLISH)
        public val EMPTY_RICH_TEXT: Localized<RichText> = Localized(RichText(""), LanguageCode.ENGLISH)
    }
}

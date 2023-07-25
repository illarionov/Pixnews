/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.test.element.util

import androidx.compose.ui.test.SemanticsMatcher

internal fun SemanticsMatcher.havingAchestor(parent: SemanticsMatcher?): SemanticsMatcher {
    if (parent == null) {
        return this
    }
    return this.and(havingAchestor(parent))
}

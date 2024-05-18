/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.model

import ru.pixnews.domain.model.util.Color

/**
 * Workaround for https://issuetracker.google.com/issues/303833208
 */
public data class ColorWrapper(
    public val color: Color,
) {
    override fun toString(): String = color.toString()
}

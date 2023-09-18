/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.builder

import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbField

@IgdbFieldDsl
public sealed class IgdbRequestFieldBuilder<out T : Any>(internal val parent: IgdbRequestField<*>? = null) {
    public val all: IgdbRequestField<out T> get() = IgdbRequestField(IgdbField.ALL, parent)
}

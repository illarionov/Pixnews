/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field

import ru.pixnews.feature.calendar.datasource.igdb.dsl.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.dsl.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbField

@IgdbFieldDsl
public sealed class IgdbRequestFields<F : IgdbField<T>, out T : Any>(
    protected val parentIgdbField: IgdbRequestField<*>? = null,
) {
    public val all: IgdbRequestField<F> get() = IgdbRequestField(IgdbField.ALL, parentIgdbField)

    public fun fieldWithId(fieldId: F): IgdbRequestField<T> = IgdbRequestField(fieldId, parentIgdbField)
}

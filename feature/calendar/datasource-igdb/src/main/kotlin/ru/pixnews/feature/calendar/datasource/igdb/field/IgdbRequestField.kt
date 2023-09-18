/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field

import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbFieldTemp

public data class IgdbRequestField<O : Any>(
    val field: IgdbField<O>,
    val parent: IgdbRequestField<*>? = null,
) {
    public val igdbName: String
        get() = if (parent == null) {
            this.field.igdbName
        } else {
            parent.igdbName + "." + this.field.igdbName
        }

    override fun toString(): String {
        return "IgdbRequestField('$igdbName')"
    }
}

public fun IgdbRequestField<*>.child(name: String): IgdbRequestField<Nothing> = IgdbRequestField(
    IgdbFieldTemp(name),
    this,
)

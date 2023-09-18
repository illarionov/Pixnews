/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field

import kotlin.reflect.KClass

public data class IgdbRequestField<O : Any>(
    val igdbFieldName: String,
    val fieldClass: KClass<O>,
    val parent: IgdbRequestField<*>? = null,
) {
    public val igdbName: String
        get() = if (parent == null) {
            this.igdbFieldName
        } else {
            parent.igdbName + "." + this.igdbFieldName
        }

    override fun toString(): String {
        return "IgdbRequestField('$igdbName')"
    }
}

public fun IgdbRequestField<*>.child(name: String): IgdbRequestField<Nothing> = IgdbRequestField(
    name,
    Nothing::class,
    this,
)

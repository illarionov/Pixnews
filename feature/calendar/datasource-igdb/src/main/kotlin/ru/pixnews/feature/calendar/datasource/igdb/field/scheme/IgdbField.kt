/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.scheme

public interface IgdbField<out O : Any> {
    public val igdbName: String

    public companion object {
        public val ALL: IgdbField<Nothing> = IgdbFieldAll

        private data object IgdbFieldAll : IgdbField<Nothing> {
            override val igdbName: String = "*"
        }
    }
}

public data class IgdbFieldTemp(
    val name: String,
) : IgdbField<Nothing> {
    override val igdbName: String = name
}

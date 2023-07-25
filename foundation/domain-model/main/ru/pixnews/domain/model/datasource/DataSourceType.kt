/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.datasource

public sealed class DataSourceType {
    public object Rawg : DataSourceType()
    public object Igdb : DataSourceType()
    public data class Other(
        val name: String,
    ) : DataSourceType()
}

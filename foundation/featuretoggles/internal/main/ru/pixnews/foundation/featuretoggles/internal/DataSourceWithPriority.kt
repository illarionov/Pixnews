/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.internal

/**
 * Data source and its priority.
 *
 * @param priority priority for the data source. Values range from -20 (highest priority) to 19 (lowest priority).
 */
public class DataSourceWithPriority(
    public val dataSource: FeatureToggleDataSource,
    public val priority: Int = 0,
) {
    init {
        @Suppress("MagicNumber")
        require(priority in -20..19)
    }
}

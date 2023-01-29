/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

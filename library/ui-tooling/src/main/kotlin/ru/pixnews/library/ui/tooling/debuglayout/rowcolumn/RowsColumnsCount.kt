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
package ru.pixnews.library.ui.tooling.debuglayout.rowcolumn

@JvmInline
public value class RowsColumnsCount(
    public val value: Int,
) {
    public companion object {
        public val Auto: RowsColumnsCount = RowsColumnsCount(0)
    }
}

public val Int.asRowColumnCount: RowsColumnsCount
    get() = RowsColumnsCount(this)

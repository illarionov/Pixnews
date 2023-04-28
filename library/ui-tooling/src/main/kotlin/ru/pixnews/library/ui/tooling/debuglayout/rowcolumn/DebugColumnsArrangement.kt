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

import androidx.compose.ui.unit.Dp
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayoutDefaults.Column

public sealed class DebugColumnsArrangement {
    public data class Left(
        val width: Dp,
        val offset: Dp = Column.offset,
        val gutter: Dp = Column.gutter,
    ) : DebugColumnsArrangement()

    public data class Right(
        val width: Dp,
        val offset: Dp = Column.offset,
        val gutter: Dp = Column.gutter,
    ) : DebugColumnsArrangement()

    public data class Center(
        val width: Dp,
        val gutter: Dp = Column.gutter,
    ) : DebugColumnsArrangement()

    public data class Stretch(
        val margin: Dp = Column.margin,
        val gutter: Dp = Column.gutter,
    ) : DebugColumnsArrangement()
}

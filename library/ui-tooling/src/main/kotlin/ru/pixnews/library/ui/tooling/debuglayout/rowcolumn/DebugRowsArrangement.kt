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
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayoutDefaults.Row

public sealed class DebugRowsArrangement {
    public data class Top(
        val height: Dp,
        val offset: Dp = Row.offset,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Bottom(
        val height: Dp,
        val offset: Dp = Row.offset,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Center(
        val height: Dp,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Stretch(
        val margin: Dp = Row.margin,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()
}

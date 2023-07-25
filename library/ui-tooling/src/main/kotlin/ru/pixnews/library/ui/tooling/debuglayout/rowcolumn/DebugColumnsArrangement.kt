/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

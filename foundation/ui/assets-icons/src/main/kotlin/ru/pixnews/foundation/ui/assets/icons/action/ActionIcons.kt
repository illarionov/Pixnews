/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object ActionIcons {
    public val Cancel: ImageVector
        get() = CancelOutlinedUnfilled400w0g24dp
}

@Preview(showBackground = true)
@Composable
private fun ActionIconsPreview() = IconsPreview(
    listOf(
        ActionIcons.Cancel,
    ),
)

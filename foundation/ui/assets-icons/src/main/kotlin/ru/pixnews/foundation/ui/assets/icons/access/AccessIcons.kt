/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.access

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object AccessIcons {
    public val public: ImageVector
        get() = PublicRoundedFilled400w0g24dp
    public val private: ImageVector
        get() = LockRoundedFilled400w0g24dp
    public val linkOnly: ImageVector
        get() = LinkRoundedFilled400w0g24dp
}

@Preview(showBackground = true)
@Composable
private fun AccessIconsPreview() = IconsPreview(
    listOf(
        AccessIcons.public,
        AccessIcons.private,
        AccessIcons.linkOnly,
    ),
)

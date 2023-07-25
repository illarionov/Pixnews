/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.instrumented.test.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width

public fun assertVerticalPaddingBetweenAdjacentItems(
    subject: String,
    expectedPadding: Dp,
    topItem: SemanticsNodeInteraction,
    bottomItem: SemanticsNodeInteraction,
) {
    val bottomFirst = topItem.getUnclippedBoundsInRoot().bottom
    val topSecond = bottomItem.getUnclippedBoundsInRoot().top

    (topSecond - bottomFirst).assertIsEqualTo(expectedPadding, subject)
}

public fun assertHorizontalPaddingBetweenAdjacentItems(
    subject: String,
    expectedPadding: Dp,
    startItem: SemanticsNodeInteraction,
    endItem: SemanticsNodeInteraction,
) {
    val leftEnd = startItem.getUnclippedBoundsInRoot().right
    val rightStart = endItem.getUnclippedBoundsInRoot().left

    (rightStart - leftEnd)
        .assertIsEqualTo(expectedPadding, subject)
}

public fun SemanticsNodeInteraction.assertTopPaddingInParentIsEqualTo(
    parent: SemanticsNodeInteraction,
    expectedTop: Dp,
): SemanticsNodeInteraction {
    (getUnclippedBoundsInRoot().top - parent.getUnclippedBoundsInRoot().top)
        .assertIsEqualTo(expectedTop, "top padding")
    return this
}

public fun SemanticsNodeInteraction.assertBottomPaddingInParentIsEqualTo(
    parent: SemanticsNodeInteraction,
    expectedBottom: Dp,
): SemanticsNodeInteraction {
    (parent.getUnclippedBoundsInRoot().height - getUnclippedBoundsInRoot().bottom)
        .assertIsEqualTo(expectedBottom, "bottom padding")
    return this
}

public fun SemanticsNodeInteraction.assertLeftPaddingInParentIsEqualTo(
    parent: SemanticsNodeInteraction,
    expectedLeft: Dp,
): SemanticsNodeInteraction {
    (getUnclippedBoundsInRoot().left - parent.getUnclippedBoundsInRoot().left)
        .assertIsEqualTo(expectedLeft, "left padding")
    return this
}

public fun SemanticsNodeInteraction.assertRightPaddingInParentIsEqualTo(
    parent: SemanticsNodeInteraction,
    expectedRight: Dp,
): SemanticsNodeInteraction {
    (parent.getUnclippedBoundsInRoot().width - getUnclippedBoundsInRoot().right)
        .assertIsEqualTo(expectedRight, "right padding")
    return this
}

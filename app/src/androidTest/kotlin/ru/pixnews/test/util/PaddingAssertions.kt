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
package ru.pixnews.test.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.unit.Dp

fun assertVerticalPaddingBetweenAdjacentItems(
    subject: String,
    expectedPadding: Dp,
    topItem: SemanticsNodeInteraction,
    bottomItem: SemanticsNodeInteraction,
) {
    val bottomFirst = topItem.getUnclippedBoundsInRoot().bottom
    val topSecond = bottomItem.getUnclippedBoundsInRoot().top

    (topSecond - bottomFirst)
        .assertIsEqualTo(expectedPadding, subject)
}

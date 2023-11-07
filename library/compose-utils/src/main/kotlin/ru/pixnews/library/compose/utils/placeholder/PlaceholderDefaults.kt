/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.compose.utils.placeholder

// Based on  com.google.accompanist.placeholder. Original license text:

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

public object PlaceholderDefaults {
    /**
     * The default [InfiniteRepeatableSpec] to use for [fade].
     */
    public val fadeAnimationSpec: InfiniteRepeatableSpec<Float> by lazy {
        infiniteRepeatable(
            animation = tween(delayMillis = 200, durationMillis = 600),
            repeatMode = Reverse,
        )
    }

    /**
     * The default [InfiniteRepeatableSpec] to use for [shimmer].
     */
    public val shimmerAnimationSpec: InfiniteRepeatableSpec<Float> by lazy {
        infiniteRepeatable(
            animation = tween(durationMillis = 1700, delayMillis = 200),
            repeatMode = Restart,
        )
    }

    /**
     * Returns the value used as the the `color` parameter value on [Modifier.placeholder].
     *
     * @param backgroundColor The current background color of the layout. Defaults to
     * `MaterialTheme.colorScheme.surface`.
     * @param contentColor The content color to be used on top of [backgroundColor].
     * @param contentAlpha The alpha component to set on [contentColor] when compositing the color
     * on top of [backgroundColor]. Defaults to `0.1f`.
     */
    @Composable
    public fun color(
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = contentColorFor(backgroundColor),
        contentAlpha: Float = 0.1f,
    ): Color = contentColor.copy(contentAlpha).compositeOver(backgroundColor)

    /**
     * Returns the value used as the the `highlightColor` parameter value of
     * [PlaceholderHighlight.Companion.fade].
     *
     * @param backgroundColor The current background color of the layout. Defaults to
     * `MaterialTheme.colorScheme.surface`.
     * @param alpha The alpha component to set on [backgroundColor]. Defaults to `0.3f`.
     */
    @Composable
    public fun fadeHighlightColor(
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        alpha: Float = 0.3f,
    ): Color = backgroundColor.copy(alpha = alpha)

    /**
     * Returns the value used as the the `highlightColor` parameter value of
     * [PlaceholderHighlight.Companion.shimmer].
     *
     * @param backgroundColor The current background color of the layout. Defaults to
     * `MaterialTheme.colorScheme.inverseSurface`.
     * @param alpha The alpha component to set on [backgroundColor]. Defaults to `0.75f`.
     */
    @Composable
    public fun shimmerHighlightColor(
        backgroundColor: Color = MaterialTheme.colorScheme.inverseSurface,
        alpha: Float = 0.75f,
    ): Color {
        return backgroundColor.copy(alpha = alpha)
    }
}

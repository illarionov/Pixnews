/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.State
import ru.pixnews.foundation.ui.imageloader.coil.compose.AsyncImage
import ru.pixnews.library.compose.utils.placeholder.PlaceholderHighlight
import ru.pixnews.library.compose.utils.placeholder.fade
import ru.pixnews.library.compose.utils.placeholder.placeholder

/**
 * [AsyncImage] with placeholder
 *
 * @param placeholderColor color the color used to draw the placeholder UI. If [Color.Unspecified] is provided,
 * the placeholder will use PlaceholderDefaults.color.
 * @param placeholderHighlight optional highlight animation.
 */
@Composable
public fun NetworkImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    error: Painter? = null,
    fallback: Painter? = error,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    placeholderColor: Color = Color.Unspecified,
    placeholderHighlight: PlaceholderHighlight? = PlaceholderHighlight.fade(),
    placeholderShape: Shape? = null,
) {
    var imageLoading by remember(model) { mutableStateOf(true) }
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier
            .placeholder(
                visible = imageLoading,
                color = placeholderColor,
                highlight = placeholderHighlight,
                shape = placeholderShape,
            ),
        placeholder = null,
        error = error,
        fallback = fallback,
        onState = {
            imageLoading = it is State.Loading
            if (onState != null) {
                onState(it)
            }
        },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}

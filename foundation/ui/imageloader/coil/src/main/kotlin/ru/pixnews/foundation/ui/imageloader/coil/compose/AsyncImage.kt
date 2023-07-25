/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("FILE_NAME_MATCH_CLASS")

package ru.pixnews.foundation.ui.imageloader.coil.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.Companion.DefaultTransform
import coil.compose.AsyncImagePainter.State
import coil.request.NullRequestDataException
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoaderHolder

// Based on coil.compose.AsyncImage

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param placeholder A [Painter] that is displayed while the image is loading.
 * @param error A [Painter] that is displayed when the image request is unsuccessful.
 * @param fallback A [Painter] that is displayed when the request's [ImageRequest.data] is null.
 * @param onState Called when the state of this painter changes.
 * @param alignment Optional alignment parameter used to place the [AsyncImagePainter] in the given
 *  bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [AsyncImagePainter] when it is rendered
 *  onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the [AsyncImagePainter] when it is
 *  rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 */
@SuppressLint("VisibleForTests")
@Composable
public fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) {
    coil.compose.AsyncImage(
        model = model,
        contentDescription = contentDescription,
        imageLoader = ImageLoaderHolder.getComposeImageLoader(),
        modifier = modifier,
        transform = transformOf(placeholder, error, fallback),
        onState = onState,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}

/**
 * A composable that executes an [ImageRequest] asynchronously and renders the result.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents. This should always be provided unless this image is used for decorative purposes,
 *  and does not represent a meaningful action that a user can take.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param transform A callback to transform a new [State] before it's applied to the
 *  [AsyncImagePainter]. Typically this is used to modify the state's [Painter].
 * @param onState Called when the state of this painter changes.
 * @param alignment Optional alignment parameter used to place the [AsyncImagePainter] in the given
 *  bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used if the bounds are a different size from the intrinsic size of the [AsyncImagePainter].
 * @param alpha Optional opacity to be applied to the [AsyncImagePainter] when it is rendered
 *  onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the [AsyncImagePainter] when it is
 *  rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the
 *  destination.
 */
@SuppressLint("VisibleForTests")
@Composable
@NonRestartableComposable
public fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
): Unit = coil.compose.AsyncImage(
    model = model,
    contentDescription = contentDescription,
    imageLoader = ImageLoaderHolder.getComposeImageLoader(),
    modifier = modifier,
    transform = transform,
    onState = onState,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality,
)

@Stable
private fun transformOf(
    placeholder: Painter?,
    error: Painter?,
    fallback: Painter?,
): (State) -> State {
    return if (placeholder != null || error != null || fallback != null) {
        DefaultStatesTransform(placeholder, error, fallback)
    } else {
        DefaultTransform
    }
}

private class DefaultStatesTransform(
    private val placeholder: Painter?,
    private val error: Painter?,
    private val fallback: Painter?,
) : (State) -> State {
    override fun invoke(state: State): State {
        return when (state) {
            is State.Loading -> if (placeholder != null) state.copy(painter = placeholder) else state

            is State.Error -> if (state.result.throwable is NullRequestDataException) {
                if (fallback != null) state.copy(painter = fallback) else state
            } else {
                if (error != null) state.copy(painter = error) else state
            }

            else -> state
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as DefaultStatesTransform

        if (placeholder != other.placeholder) {
            return false
        }
        if (error != other.error) {
            return false
        }
        if (fallback != other.fallback) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = placeholder?.hashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + (fallback?.hashCode() ?: 0)
        return result
    }
}

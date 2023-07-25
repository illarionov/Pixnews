/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("WRONG_OVERLOADING_FUNCTION_ARGUMENTS")

package ru.pixnews.domain.model.util

public interface VideoUrl {
    public val size: CanvasSize?
        get() = null
    public fun getUrl(): String
    public fun getUrl(width: Dimension, height: Dimension): String
}

public data class DefaultVideoUrl(
    val rawUrl: String,
) : VideoUrl {
    override fun getUrl(): String = rawUrl
    override fun getUrl(width: Dimension, height: Dimension): String = rawUrl
}

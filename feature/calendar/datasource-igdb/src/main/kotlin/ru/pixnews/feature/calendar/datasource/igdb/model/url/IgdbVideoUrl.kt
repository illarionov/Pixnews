/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.model.url

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import ru.pixnews.domain.model.url.VideoUrl
import ru.pixnews.domain.model.util.Dimension
import kotlin.LazyThreadSafetyMode.PUBLICATION

@Suppress("WRONG_OVERLOADING_FUNCTION_ARGUMENTS")
internal data class IgdbVideoUrl(
    val igdbVideoId: String,
) : VideoUrl {
    private val url: String by lazy(PUBLICATION) {
        igdbVideoId.let {
            if (it.isValidUrl()) {
                it
            } else {
                getYoutubeUrl(it)
            }
        }
    }

    override fun getUrl(width: Dimension, height: Dimension): String = url

    private companion object {
        val youtubeBase: HttpUrl = "https://www.youtube.com/watch".toHttpUrl()

        fun String.isValidUrl(): Boolean = this.toHttpUrlOrNull() != null

        fun getYoutubeUrl(videoId: String): String = youtubeBase.newBuilder()
            .addQueryParameter("v", videoId)
            .toString()
    }
}

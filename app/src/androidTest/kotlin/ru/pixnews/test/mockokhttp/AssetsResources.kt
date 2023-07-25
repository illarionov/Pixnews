/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.test.mockokhttp

import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import java.net.URLConnection

internal object AssetsResources {
    fun getResource(url: HttpUrl): AssetsResource {
        return AssetsResource.forUrl(url)
    }

    internal data class AssetsResource(
        val url: HttpUrl,
        val assetsPrefix: String,
        val mediaType: MediaType = "image/png".toMediaType(),
    ) {
        companion object {
            fun forUrl(httpUrl: HttpUrl): AssetsResource {
                var segments = httpUrl.pathSegments
                if (segments.last().isEmpty()) {
                    segments = segments.dropLast(1) + "index.html"
                }
                val assetsPrefix = "fixtures/${httpUrl.host}/${segments.joinToString("/")}"
                val mimeType = URLConnection.guessContentTypeFromName(segments.last()).toMediaType()
                return AssetsResource(url = httpUrl, assetsPrefix = assetsPrefix, mediaType = mimeType)
            }
        }
    }
}

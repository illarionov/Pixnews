/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.assets

import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import java.net.URLConnection

public data class AssetsResource(
    val url: HttpUrl,
    val assetsPrefix: String,
    val mediaType: MediaType = "image/png".toMediaType(),
) {
    public companion object {
        public fun forUrl(
            httpUrl: HttpUrl,
            originalHost: String = httpUrl.host,
        ): AssetsResource {
            var segments = httpUrl.pathSegments
            if (segments.last().isEmpty()) {
                segments = segments.dropLast(1) + "index.html"
            }
            val assetsPrefix = "fixtures/$originalHost/${segments.joinToString("/")}"
            val mimeType = URLConnection.guessContentTypeFromName(segments.last()).toMediaType()
            return AssetsResource(url = httpUrl, assetsPrefix = assetsPrefix, mediaType = mimeType)
        }
    }
}

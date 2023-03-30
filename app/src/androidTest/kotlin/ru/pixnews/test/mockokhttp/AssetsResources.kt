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

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.assets

import android.content.res.AssetManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import okio.FileNotFoundException

internal class ResponseFromAssetsReader(
    val assets: () -> AssetManager,
    val originalHost: String? = null,
) : (RecordedRequest) -> MockResponse {
    override fun invoke(recorderRequest: RecordedRequest): MockResponse {
        val requestUrl = checkNotNull(recorderRequest.requestUrl) { "Undefined request URL" }
        val assetsResource = AssetsResource.forUrl(
            requestUrl,
            originalHost ?: requestUrl.host,
        )
        return try {
            val buffer = assets().openFd(assetsResource.assetsPrefix).createInputStream().use {
                Buffer().readFrom(it)
            }
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", assetsResource.mediaType)
                .setBody(buffer)
        } catch (_: FileNotFoundException) {
            MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "text/plain".toMediaType())
                .setBody("File ${assetsResource.assetsPrefix} not found in assets")
        }
    }
}

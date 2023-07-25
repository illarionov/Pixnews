/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import coil.intercept.Interceptor
import coil.intercept.Interceptor.Chain
import coil.request.ImageRequest
import coil.request.ImageResult
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.foundation.ui.imageloader.coil.mapper.toDimensionModel

@RestrictTo(LIBRARY_GROUP)
public class ImageUrlCoilInterceptor : Interceptor {
    override suspend fun intercept(chain: Chain): ImageResult {
        val request = when (val data = chain.request.data) {
            is ImageUrl -> handle(chain, data)
            else -> chain.request
        }
        return chain.proceed(request)
    }

    private fun handle(chain: Chain, model: ImageUrl): ImageRequest {
        val url = model.getUrl(
            chain.size.width.toDimensionModel(),
            chain.size.height.toDimensionModel(),
        )

        return chain.request.newBuilder()
            .data(url)
            .build()
    }
}

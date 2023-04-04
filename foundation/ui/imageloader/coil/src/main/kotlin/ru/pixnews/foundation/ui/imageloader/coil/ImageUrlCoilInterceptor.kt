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

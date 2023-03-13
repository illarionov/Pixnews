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
package ru.pixnews.foundation.ui.imageloader.coil.tooling

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import coil.decode.DataSource.NETWORK
import coil.intercept.Interceptor
import coil.intercept.Interceptor.Chain
import coil.request.ErrorResult
import coil.request.ImageResult
import coil.request.SuccessResult
import kotlinx.coroutines.delay
import ru.pixnews.foundation.ui.imageloader.coil.tooling.OverrideResult.Error
import ru.pixnews.foundation.ui.imageloader.coil.tooling.OverrideResult.Original
import ru.pixnews.foundation.ui.imageloader.coil.tooling.OverrideResult.Success
import kotlin.time.Duration.Companion.ZERO

internal class CoilDebugInterceptor : Interceptor {
    override suspend fun intercept(chain: Chain): ImageResult {
        val model = chain.request.data
        if (model !is DebugImageUrl) {
            return chain.proceed(chain.request)
        }

        return handle(chain, model)
    }

    private suspend fun handle(chain: Chain, model: DebugImageUrl): ImageResult {
        if (model.loadingDelay != ZERO) {
            delay(model.loadingDelay)
        }

        val request = chain.request.newBuilder()
            .data(model.originalUrl)
            .build()

        val result = chain.proceed(request)
        return when (model.overriddenResult) {
            Original -> result

            is Error -> when (result) {
                    is ErrorResult -> if (model.overriddenResult.throwable != null) {
                            result.copy(throwable = model.overriddenResult.throwable)
                        } else {
                            result
                        }

                    is SuccessResult -> ErrorResult(
                            drawable = result.drawable,
                            request = result.request,
                            throwable = model.overriddenResult.throwable ?: RuntimeException("Overridden result"),
                        )
                }

            is Success -> when (result) {
                    is ErrorResult -> SuccessResult(
                            drawable = model.overriddenResult.drawable
                                ?: result.drawable
                                ?: ColorDrawable(Color.LTGRAY),
                            request = result.request,
                            dataSource = NETWORK,
                        )

                    is SuccessResult -> if (model.overriddenResult.drawable != null) {
                            result.copy(drawable = model.overriddenResult.drawable)
                        } else {
                            result
                        }
                }
        }
    }
}

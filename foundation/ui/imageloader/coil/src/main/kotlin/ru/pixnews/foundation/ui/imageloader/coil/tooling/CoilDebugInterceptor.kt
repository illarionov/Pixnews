/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil.tooling

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
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

@RestrictTo(LIBRARY_GROUP)
public class CoilDebugInterceptor : Interceptor {
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

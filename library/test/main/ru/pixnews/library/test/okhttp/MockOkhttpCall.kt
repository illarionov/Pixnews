/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.test.okhttp

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import okio.Timeout

public open class MockOkhttpCall(
    private val request: Request,
    private val responseFactory: (Call) -> Result<Response>,
) : Call {
    override fun cancel(): Unit = Unit

    override fun clone(): Call = MockOkhttpCall(request, responseFactory)

    override fun enqueue(responseCallback: Callback) {
        val response = responseFactory(this)
        response.fold(
            onSuccess = {
                responseCallback.onResponse(this, it)
            },
            onFailure = {
                responseCallback.onFailure(
                    this,
                    if (it is IOException) it else IOException(it),
                )
            },
        )
    }

    override fun execute(): Response = responseFactory(this).getOrThrow()

    override fun isCanceled(): Boolean = false

    override fun isExecuted(): Boolean = true

    override fun request(): Request = request

    override fun timeout(): Timeout = Timeout.NONE

    public companion object {
        public fun factory(
            responseFactory: (Call) -> Result<Response>,
        ): Call.Factory = Call.Factory { MockOkhttpCall(it, responseFactory) }
    }
}

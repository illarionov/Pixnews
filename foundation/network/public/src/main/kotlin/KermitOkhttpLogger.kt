/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network

import co.touchlab.kermit.Logger
import okhttp3.logging.HttpLoggingInterceptor

internal class KermitOkhttpLogger(
    kermitLogger: Logger,
    tag: String = "okhttp",
) : HttpLoggingInterceptor.Logger {
    private val logger = kermitLogger.withTag(tag)
    override fun log(message: String) = logger.d(message)
}

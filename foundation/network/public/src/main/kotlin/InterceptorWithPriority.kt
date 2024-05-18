/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import ru.pixnews.foundation.network.InterceptorWithPriority.InterceptorPriority

/**
 * [Interceptor] with priority.
 */
public interface InterceptorWithPriority : Interceptor {
    /**
     * Priority for the interceptor. Values range from -20 (highest priority) to 19 (lowest priority).
     */
    public val priority: InterceptorPriority

    @JvmInline
    public value class InterceptorPriority(public val intValue: Int) : Comparable<InterceptorPriority> {
        init {
            @Suppress("MagicNumber")
            require(intValue in -20..19)
        }

        override fun compareTo(other: InterceptorPriority): Int = intValue.compareTo(other.intValue)
    }
}

public fun Interceptor.withPriority(priority: Int): InterceptorWithPriority = object : InterceptorWithPriority {
    override val priority: InterceptorPriority = InterceptorPriority(priority)
    override fun intercept(chain: Chain): Response = this@withPriority.intercept(chain)
}

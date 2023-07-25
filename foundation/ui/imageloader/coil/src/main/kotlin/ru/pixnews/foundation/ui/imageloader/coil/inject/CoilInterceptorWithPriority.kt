/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil.inject

import coil.intercept.Interceptor

public class CoilInterceptorWithPriority(
    public val interceptor: Interceptor,
    public val priority: Int,
) {
    init {
        @Suppress("MagicNumber")
        require(priority in -20..19)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as CoilInterceptorWithPriority

        if (interceptor != other.interceptor) {
            return false
        }
        if (priority != other.priority) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = interceptor.hashCode()
        result = 31 * result + priority
        return result
    }
}

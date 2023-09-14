/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.test.okhttp

import okio.Buffer
import okio.source

public fun Any.readResourceAsBuffer(path: String): Buffer {
    return Buffer().apply {
        val stream = this@readResourceAsBuffer.javaClass.getResourceAsStream(path) ?: error("No resource `$path`")
        stream.source().use {
            writeAll(it)
        }
    }
}

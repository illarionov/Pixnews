/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.mockokhttp

public class NetworkBehavior {
    private val lock = Any()

    public var networkConnected: Boolean = DEFAULT_NETWORK_CONNECTED
        get() = synchronized(lock) { field }
        set(value) = synchronized(lock) { field = value }

    public fun reset(): Unit = synchronized(lock) {
        networkConnected = DEFAULT_NETWORK_CONNECTED
    }

    private companion object {
        const val DEFAULT_NETWORK_CONNECTED = true
    }
}

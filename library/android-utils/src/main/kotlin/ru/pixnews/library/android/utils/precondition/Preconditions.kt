/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.android.utils.precondition

import android.os.Looper

@Suppress("NOTHING_TO_INLINE")
public inline fun checkNotMainThread(): Unit = checkNotMainThread {
    "Needs to be called not on main thread"
}

public inline fun checkNotMainThread(lazyMessage: () -> Any) {
    check(Looper.myLooper() != Looper.getMainLooper(), lazyMessage)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun checkMainThread(): Unit = checkMainThread {
    "Needs to be called on main thread"
}

public inline fun checkMainThread(lazyMessage: () -> Any) {
    check(Looper.myLooper() == Looper.getMainLooper(), lazyMessage)
}

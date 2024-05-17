/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.di.root.component

public object PixnewsRootComponentHolder {
    private var _appComponent: PixnewsAppComponent? = null
    public val appComponent: PixnewsAppComponent
        get() = synchronized(PixnewsRootComponentHolder::class) {
            return _appComponent ?: error("Component not initialized")
        }

    public fun <A : PixnewsAppComponent> init(factory: () -> A): A {
        return synchronized(PixnewsRootComponentHolder::class) {
            val component = _appComponent ?: factory().also {
                _appComponent = it
            }
            @Suppress("UNCHECKED_CAST")
            component as A
        }
    }
}

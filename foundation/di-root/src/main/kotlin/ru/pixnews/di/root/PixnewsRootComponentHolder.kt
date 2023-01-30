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
package ru.pixnews.di.root

import ru.pixnews.di.root.components.PixnewsAppComponent

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

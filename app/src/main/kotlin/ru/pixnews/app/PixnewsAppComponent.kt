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
package ru.pixnews.app

import android.app.Application
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import ru.pixnews.MainActivityComponent
import ru.pixnews.foundation.di.scopes.AppScope
import ru.pixnews.foundation.di.scopes.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface PixnewsAppComponent {
    fun inject(app: PixnewsAndroidApplication)

    fun mainActivityComponentFactory(): MainActivityComponent.Factory

    @Component.Factory
    fun interface Factory {
        fun create(@BindsInstance application: Application): PixnewsAppComponent
    }
}
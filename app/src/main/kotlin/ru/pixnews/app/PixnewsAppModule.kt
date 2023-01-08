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
import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import ru.pixnews.di.qualifiers.ApplicationContext
import ru.pixnews.di.scopes.AppScope
import ru.pixnews.di.scopes.SingleIn

@ContributesTo(AppScope::class)
@Module
abstract class PixnewsAppModule {
    @Binds
    @ApplicationContext
    @SingleIn(AppScope::class)
    abstract fun Application.provideApplicationContext(): Context
}

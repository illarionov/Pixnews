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
package ru.pixnews

import android.app.Application
import androidx.work.Configuration
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.inject.MainPixnewsAppComponent
import javax.inject.Inject

class PixnewsApplication : Application(), Configuration.Provider {
    @field:Inject
    lateinit var localWorkManagerConfiguration: Configuration

    override fun onCreate() {
        super.onCreate()
        (PixnewsRootComponentHolder.appComponent as MainPixnewsAppComponent).inject(this)
    }

    override fun getWorkManagerConfiguration(): Configuration = localWorkManagerConfiguration
}

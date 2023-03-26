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

import android.os.Build
import com.squareup.anvil.annotations.ContributesBinding
import ru.pixnews.BuildConfig
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.HttpLoggingLevel
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.scopes.AppScope
import kotlin.LazyThreadSafetyMode.NONE

@ContributesBinding(AppScope::class, replaces = [PixnewsAppConfig::class])
public object TestPixnewsAppConfig : AppConfig {
    override val isDebug: Boolean = BuildConfig.DEBUG
    override val applicationId: String = BuildConfig.APPLICATION_ID
    override val buildType: String = BuildConfig.BUILD_TYPE
    override val versionName: String = BuildConfig.VERSION_NAME
    override val versionCode: Int = BuildConfig.VERSION_CODE
    override val timestamp: String by lazy(NONE) { "TODO" }
    override val sdkInt: Int = Build.VERSION.SDK_INT
    override val networkConfig: NetworkConfig by lazy(NONE) {
        object : NetworkConfig {
            override val httpLoggingLevel: HttpLoggingLevel = HttpLoggingLevel.HEADERS
        }
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import android.os.Build
import com.squareup.anvil.annotations.ContributesBinding
import ru.pixnews.BuildConfig
import ru.pixnews.config.GeneratedIgdbClientConfig
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.IgdbClientConfig
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.scopes.AppScope
import kotlin.LazyThreadSafetyMode.NONE

@ContributesBinding(AppScope::class)
public object PixnewsAppConfig : AppConfig {
    override val isDebug: Boolean = BuildConfig.DEBUG
    override val applicationId: String = BuildConfig.APPLICATION_ID
    override val buildType: String = BuildConfig.BUILD_TYPE
    override val versionName: String = BuildConfig.VERSION_NAME
    override val versionCode: Int = BuildConfig.VERSION_CODE
    override val timestamp: String by lazy(NONE) { "TODO" }
    override val sdkInt: Int = Build.VERSION.SDK_INT
    override val networkConfig: NetworkConfig by lazy(NONE) {
        object : NetworkConfig {
        }
    }
    override val igdbClientConfig: IgdbClientConfig = GeneratedIgdbClientConfig
}

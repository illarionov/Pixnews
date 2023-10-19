/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import android.os.Build
import com.squareup.anvil.annotations.ContributesBinding
import ru.pixnews.BuildConfig
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.HttpLoggingLevel
import ru.pixnews.foundation.appconfig.IgdbClientConfig
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.test.app.mock.igdb.IgdbMockWebServer
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

@ContributesBinding(AppScope::class, replaces = [PixnewsAppConfig::class])
public class TestPixnewsAppConfig @Inject constructor(
    val igdbMockWebServer: IgdbMockWebServer,
) : AppConfig {
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
    override val igdbClientConfig: IgdbClientConfig = object : IgdbClientConfig {
        override val baseUrl: String
            get() = igdbMockWebServer.webServer.url("/v4/").toString()
    }
}

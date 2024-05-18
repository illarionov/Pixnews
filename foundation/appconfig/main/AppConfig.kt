/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.appconfig

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

public interface AppConfig {
    public val isDebug: Boolean
    public val applicationId: String
    public val buildType: String
    public val versionName: String
    public val versionCode: Int
    public val timestamp: String
    public val sdkInt: Int
    public val networkConfig: NetworkConfig
    public val igdbClientConfig: IgdbClientConfig
}

public interface NetworkConfig {
    public val httpLoggingLevel: HttpLoggingLevel get() = HttpLoggingLevel.NONE
    public val connectTimeout: Duration get() = 20.seconds
    public val readTimeout: Duration get() = 20.seconds
    public val writeTimeout: Duration get() = 20.seconds
    public val cacheSizeMbytes: ULong get() = 10U
    public val imageCacheSizeMbytes: ULong get() = 50U

    public val connectionPoolMaxIdleConnections: UInt get() = 10U
    public val connectionPoolKeepAliveTimeout: Duration get() = 2.minutes

    public val maxConnectionsPerHost: UInt get() = 10U
}

public interface IgdbClientConfig {
    public val baseUrl: String get() = "https://api.igdb.com/v4/"
    public val apiKey: String get() = ""

    /**
     * Twitch auth (debug only)
     */
    public val twitchAuth: TwitchAuth? get() = null

    public interface TwitchAuth {
        public val clientId: String get() = ""
        public val clientSecret: String get() = ""
        public val token: String? get() = null
    }
}

public enum class HttpLoggingLevel {
    NONE, BASIC, HEADERS, BODY
}

public open class DefaultAppConfig : AppConfig {
    override val isDebug: Boolean
        get() = throw NotImplementedError()
    override val applicationId: String
        get() = throw NotImplementedError()
    override val buildType: String
        get() = throw NotImplementedError()
    override val versionName: String
        get() = throw NotImplementedError()
    override val versionCode: Int
        get() = throw NotImplementedError()
    override val timestamp: String
        get() = throw NotImplementedError()
    override val sdkInt: Int
        get() = throw NotImplementedError()
    override val networkConfig: NetworkConfig
        get() = throw NotImplementedError()
    override val igdbClientConfig: IgdbClientConfig
        get() = throw NotImplementedError()
}

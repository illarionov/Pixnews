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
package ru.pixnews.appconfig

public interface AppConfig {
    public val isDebug: Boolean
    public val applicationId: String
    public val buildType: String
    public val versionName: String
    public val versionCode: Int
    public val timestamp: String
    public val sdkInt: Int
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
}

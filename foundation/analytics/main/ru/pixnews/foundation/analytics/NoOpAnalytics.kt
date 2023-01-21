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
package ru.pixnews.foundation.analytics

public class NoOpAnalytics : Analytics {
    public override fun setCurrentScreenName(screenName: String, screenClass: String): Unit = Unit

    public override fun setEnableAnalytics(enable: Boolean): Unit = Unit

    public override fun setUserId(userId: String?): Unit = Unit

    public override fun setUserProperty(name: String, value: String): Unit = Unit

    public override fun logEvent(name: String, params: Map<String, *>?): Unit = Unit
}

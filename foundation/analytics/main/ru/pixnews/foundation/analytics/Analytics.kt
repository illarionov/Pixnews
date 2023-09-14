/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.analytics

public interface Analytics {
    public fun setCurrentScreenName(screenName: String, screenClass: String)

    public fun setEnableAnalytics(enable: Boolean)

    public fun setUserId(userId: String?)

    public fun setUserProperty(name: String, value: String)

    public fun logEvent(name: String, params: Map<String, *>? = null)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.analytics

public class NoOpAnalytics : Analytics {
    public override fun setCurrentScreenName(screenName: String, screenClass: String): Unit = Unit

    public override fun setEnableAnalytics(enable: Boolean): Unit = Unit

    public override fun setUserId(userId: String?): Unit = Unit

    public override fun setUserProperty(name: String, value: String): Unit = Unit

    public override fun logEvent(name: String, params: Map<String, *>?): Unit = Unit
}

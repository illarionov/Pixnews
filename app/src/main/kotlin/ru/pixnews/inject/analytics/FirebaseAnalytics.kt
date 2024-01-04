/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.analytics

import androidx.core.os.bundleOf
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import ru.pixnews.foundation.analytics.Analytics

object FirebaseAnalytics : Analytics {
    private val analytics = Firebase.analytics

    override fun setEnableAnalytics(enable: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enable)
    }

    override fun setUserId(userId: String?) {
        analytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    override fun logEvent(name: String, params: Map<String, *>?) {
        val paramsBundle = if (params != null) {
            bundleOf(pairs = params.entries.map { it.key to it.value }.toTypedArray())
        } else {
            null
        }
        analytics.logEvent(name, paramsBundle)
    }
}

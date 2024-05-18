/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.analytics

import co.touchlab.kermit.Logger
import ru.pixnews.foundation.analytics.Analytics
import javax.inject.Inject

class LoggingAnalytics @Inject constructor(
    logger: Logger,
) : Analytics {
    private val log: Logger = logger.withTag("Analytics")
    override fun setEnableAnalytics(enable: Boolean) {
        log.i { "setEnableAnalytics($enable)" }
    }

    override fun setUserId(userId: String?) {
        log.i { "setUserId($userId)" }
    }

    override fun setUserProperty(name: String, value: String) {
        log.i { "setUserProperty($name, $value)" }
    }

    override fun logEvent(name: String, params: Map<String, *>?) {
        log.i { "Event `$name`: $params" }
    }
}

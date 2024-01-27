/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.sync.policy

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

public fun SyncPolicy.isSyncRequired(
    lastSyncTime: Instant,
    currentTime: Instant = Clock.System.now(),
    forceSync: Boolean = false,
    forceFullReload: Boolean = false,
): SyncRequiredResult {
    if (forceSync || forceFullReload) {
        return SyncRequiredResult.Required(isForced = true, reason = "Forced")
    }

    val nextSyncMinTime = lastSyncTime + this.minPeriod
    if (nextSyncMinTime > currentTime) {
        return SyncRequiredResult.NotRequired(reason = "Next sync not earlier than $nextSyncMinTime")
    }

    return SyncRequiredResult.Required(isForced = false, reason = "")
}

public sealed class SyncRequiredResult(
    public open val reason: String = "",
) {
    public data class Required(
        val isForced: Boolean? = null,
        override val reason: String = "",
    ) : SyncRequiredResult(reason)

    public data class NotRequired(
        override val reason: String = "",
    ) : SyncRequiredResult(reason)
}

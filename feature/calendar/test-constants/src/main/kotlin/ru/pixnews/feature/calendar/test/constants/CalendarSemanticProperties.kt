/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.test.constants

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

public val UpcomingReleaseGroupIdKey: SemanticsPropertyKey<UpcomingReleaseGroupId> =
    SemanticsPropertyKey("UpcomingReleaseGroupKey")
public var SemanticsPropertyReceiver.upcomingReleaseGroup: UpcomingReleaseGroupId by UpcomingReleaseGroupIdKey

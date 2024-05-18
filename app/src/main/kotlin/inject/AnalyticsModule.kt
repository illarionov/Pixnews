/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Reusable
import ru.pixnews.foundation.analytics.Analytics
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.inject.analytics.LoggingAnalytics

@ContributesTo(AppScope::class)
@Module
interface AnalyticsModule {
    @Binds
    @Reusable
    fun bindAnalytics(analytics: LoggingAnalytics): Analytics
}

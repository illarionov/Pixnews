/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.app.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.analytics.Analytics
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.inject.AnalyticsModule
import ru.pixnews.inject.analytics.FirebaseAnalytics

@ContributesTo(AppScope::class, replaces = [AnalyticsModule::class])
@Module
public object FirebaseAnalyticsModule {
    @Provides
    @Reusable
    fun bindsAnalytics(): Analytics = FirebaseAnalytics
}

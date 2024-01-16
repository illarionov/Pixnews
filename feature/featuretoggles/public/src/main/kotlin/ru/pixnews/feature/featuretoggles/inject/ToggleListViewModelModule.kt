/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.anvil.codegen.viewmodel.inject.wiring.ViewModelScope
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource

@ContributesTo(ViewModelScope::class)
@Module
public object ToggleListViewModelModule {
    @Provides
    @SingleIn(ViewModelScope::class)
    public fun providesOverridesDataSource(
        @ApplicationContext context: Context,
        serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
        rootCoroutineScope: RootCoroutineScope,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ): OverridesDataSource {
        return OverridesDataSource(context, serializers, rootCoroutineScope, ioDispatcherProvider.get(), logger)
    }
}

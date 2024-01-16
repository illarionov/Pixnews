/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.di.base.scopes.AppScope

@Module
@ContributesTo(AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public object PixnewsViewModelProviderFactoryModule {
    @Reusable
    @Provides
    public fun provideViewModelFactory(
        vmSubcomponentFactory: ViewModelSubcomponent.Factory,
    ): ViewModelProvider.Factory = PixnewsViewModelProviderFactory(vmSubcomponentFactory)
}

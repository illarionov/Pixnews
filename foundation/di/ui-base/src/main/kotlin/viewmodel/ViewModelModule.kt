/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.multibindings.Multibinds
import ru.pixnews.anvil.codegen.viewmodel.inject.ViewModelScope
import ru.pixnews.anvil.codegen.viewmodel.inject.wiring.ViewModelFactory
import ru.pixnews.foundation.di.base.DaggerMap

@Module
@ContributesTo(ViewModelScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface ViewModelModule {
    @Multibinds
    public fun viewModelProviders(): DaggerMap<Class<out ViewModel>, ViewModel>

    @Multibinds
    public fun viewModelFactoryProviders(): DaggerMap<Class<out ViewModel>, ViewModelFactory>
}

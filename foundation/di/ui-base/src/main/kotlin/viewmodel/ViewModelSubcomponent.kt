/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.BindsInstance
import ru.pixnews.anvil.codegen.viewmodel.inject.ViewModelScope
import ru.pixnews.anvil.codegen.viewmodel.inject.wiring.ViewModelFactory
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Provider

@ContributesSubcomponent(scope = ViewModelScope::class, parentScope = AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
@SingleIn(ViewModelScope::class)
public interface ViewModelSubcomponent {
    public val viewModelMap: DaggerMap<Class<out ViewModel>, Provider<ViewModel>>
    public val viewModelFactoryMap: DaggerMap<Class<out ViewModel>, ViewModelFactory>

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(@BindsInstance savedStateHandle: SavedStateHandle): ViewModelSubcomponent
    }

    @ContributesTo(AppScope::class)
    public interface ViewModelSubcomponentFactoryHolder {
        public fun getViewModelSubcomponentFactory(): ViewModelSubcomponent.Factory
    }
}

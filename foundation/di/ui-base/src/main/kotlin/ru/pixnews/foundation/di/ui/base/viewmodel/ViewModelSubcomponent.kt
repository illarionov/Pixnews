/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import javax.inject.Provider

@SingleIn(ViewModelScope::class)
@ContributesSubcomponent(scope = ViewModelScope::class, parentScope = AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface ViewModelSubcomponent {
    public val viewModelMap: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(@BindsInstance savedStateHandle: SavedStateHandle): ViewModelSubcomponent
    }

    @ContributesTo(AppScope::class)
    public fun interface ProviderInParentComponent {
        public fun viewModelSubcomponentFactory(): ViewModelSubcomponent.Factory
    }
}

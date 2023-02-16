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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

@Reusable
@ContributesBinding(AppScope::class, boundType = ViewModelProvider.Factory::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PixnewsViewModelProviderFactory @Inject constructor(
    private val vmSubcomponentFactory: ViewModelSubcomponent.Factory,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedSateHandle = extras.createSavedStateHandle()
        val viewModelComponent = vmSubcomponentFactory.create(savedSateHandle)
        val viewModelMap = viewModelComponent.viewModelMap

        val viewModelProvider = viewModelMap[modelClass]
        if (viewModelProvider != null) {
            @Suppress("UNCHECKED_CAST")
            return viewModelProvider.get() as T
        } else {
            val factory = viewModelComponent.viewModelFactoryMap[modelClass]
                ?: error("No factory for ${modelClass.name}")
            return factory.create(extras) as T
        }
    }
}

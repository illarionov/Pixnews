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
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

@Reusable
@ContributesBinding(AppScope::class, boundType = ViewModelProvider.Factory::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PixnewsViewModelFactory @Inject constructor(
    private val vmSubcomponentFactory: ViewModelSubcomponent.Factory,
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        val viewModelComponent = vmSubcomponentFactory.create(handle)
        val viewModelMap = viewModelComponent.viewModelMap

        val viewModelProvider = viewModelMap.getOrElse(modelClass) {
            error("No injector for ${modelClass.name}")
        }
        @Suppress("UNCHECKED_CAST")
        return viewModelProvider.get() as T
    }
}

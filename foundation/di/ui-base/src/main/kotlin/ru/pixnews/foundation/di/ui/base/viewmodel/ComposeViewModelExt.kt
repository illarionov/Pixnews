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

import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.pixnews.di.root.component.PixnewsRootComponentHolder

/**
 * Returns an existing [ViewModel] or creates a new one in the given owner (usually, a fragment or
 * an activity), defaulting to the owner provided by [LocalViewModelStoreOwner].
 *
 * The created [ViewModel] is associated with the given [viewModelStoreOwner] and will be retained
 * as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * If default arguments are provided via the [CreationExtras], they will be available to the
 * appropriate factory when the [ViewModel] is created.
 *
 * Application's [ViewModelProvider.Factory] will be used to create the [ViewModel].
 *
 * @param viewModelStoreOwner The owner of the [ViewModel] that controls the scope and lifetime
 * of the returned [ViewModel]. Defaults to using [LocalViewModelStoreOwner].
 * @param key The key to use to identify the [ViewModel].
 * @param extras The default extras used to create the [ViewModel].
 * @return A [ViewModel] that is an instance of the given [VM] type.
 */
@Composable
public inline fun <reified VM : ViewModel> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    extras: CreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    },
): VM = injectedViewModel(VM::class.java, viewModelStoreOwner, key, extras)

/**
 * Returns an existing [ViewModel] or creates a new one in the scope (usually, a fragment or
 * an activity)
 *
 * The created [ViewModel] is associated with the given [viewModelStoreOwner] and will be retained
 * as long as the scope is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * If default arguments are provided via the [CreationExtras], they will be available to the
 * appropriate factory when the [ViewModel] is created.
 *
 * Application's [ViewModelProvider.Factory] will be used to create the [ViewModel].
 *
 * @param modelClass The class of the [ViewModel] to create an instance of it if it is not
 * present.
 * @param viewModelStoreOwner The scope that the created [ViewModel] should be associated with.
 * @param key The key to use to identify the [ViewModel].
 * @param extras The default extras used to create the [ViewModel].
 * @return A [ViewModel] that is an instance of the given [VM] type.
 */
@Composable
public fun <VM : ViewModel> injectedViewModel(
    modelClass: Class<VM>,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    extras: CreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    },
): VM = viewModel(modelClass, viewModelStoreOwner, key, PixnewsRootComponentHolder.viewModelFactory, extras)

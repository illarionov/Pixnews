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
@file:Suppress("KDOC_WITHOUT_PARAM_TAG")

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import ru.pixnews.di.root.component.PixnewsRootComponentHolder

/**
 * [ComponentActivity.viewModels] that uses application's [ViewModelProvider.Factory]
 */
@MainThread
public inline fun <reified VM : ViewModel> ComponentActivity.injectedViewModel(
    noinline extrasProducer: (() -> CreationExtras) = { defaultViewModelCreationExtras },
): Lazy<VM> = ViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { viewModelStore },
    factoryProducer = PixnewsRootComponentHolder::viewModelFactory,
    extrasProducer = extrasProducer,
)

/**
 * [Fragment.viewModels] that uses application's [ViewModelProvider.Factory]
 */
@MainThread
public inline fun <reified VM : ViewModel> Fragment.injectedViewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline extrasProducer: (() -> CreationExtras)? = null,
): Lazy<VM> = viewModels(
    ownerProducer = ownerProducer,
    extrasProducer = extrasProducer,
    factoryProducer = PixnewsRootComponentHolder::viewModelFactory,
)

/**
 * [Fragment.activityViewModels] that uses application's [ViewModelProvider.Factory]
 */
@MainThread
public inline fun <reified VM : ViewModel> Fragment.injectedActivityViewModel(
    noinline extrasProducer: (() -> CreationExtras)? = null,
): Lazy<VM> = activityViewModels(
    extrasProducer = extrasProducer,
    factoryProducer = PixnewsRootComponentHolder::viewModelFactory,
)

public val PixnewsRootComponentHolder.viewModelFactory: ViewModelProvider.Factory
    get() = (this.appComponent as ViewModelFactoryInParentComponent).viewModelFactory

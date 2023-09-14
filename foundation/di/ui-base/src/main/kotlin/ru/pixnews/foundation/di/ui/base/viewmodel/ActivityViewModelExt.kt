/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("KDOC_WITHOUT_PARAM_TAG", "UnusedImports")

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
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

public val PixnewsRootComponentHolder.viewModelFactory: ViewModelProvider.Factory
    get() = (this.appComponent as ViewModelProviderFactoryHolder).viewModelFactory

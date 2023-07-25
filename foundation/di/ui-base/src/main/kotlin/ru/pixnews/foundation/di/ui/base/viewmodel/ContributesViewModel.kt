/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.ui.base.viewmodel

/**
 * Annotate a ViewModel class with this to automatically contribute it to the ViewModel scope multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 *   @Provides
 *   @IntoMap
 *   @ViewModelKey(MainViewModel::class)
 *   public fun providesMainViewModel(
 *       <arguments>
 *   ): ViewModelFactory = ViewModelFactory {
 *       MainViewModel(
 *           <arguments>
 *       )
 *   }
 *```
 * The generated code created via the anvil-codegen module.
 */
public annotation class ContributesViewModel

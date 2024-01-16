/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

internal class PixnewsViewModelProviderFactory(
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
            @Suppress("UNCHECKED_CAST")
            return factory.create(extras) as T
        }
    }
}

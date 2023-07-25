/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * A Dagger multi-binding key used for registering a [ViewModel] into the top level dagger graphs.
 */
@MapKey
public annotation class ViewModelMapKey(val viewModelClass: KClass<out ViewModel>)

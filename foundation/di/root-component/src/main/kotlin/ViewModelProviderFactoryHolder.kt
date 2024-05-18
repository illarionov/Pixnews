/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.di.root.component

import androidx.lifecycle.ViewModelProvider

public interface ViewModelProviderFactoryHolder {
    public val viewModelFactory: ViewModelProvider.Factory
}

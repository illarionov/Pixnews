/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.imageloader.coil

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.foundation.ui.imageloader.coil.inject.ImageLoaderProviderHolder
import kotlin.LazyThreadSafetyMode.PUBLICATION

/**
 * For use only from compose functions
 */
@VisibleForTesting
public object ImageLoaderHolder {
    @Volatile
    private var imageLoaderInitializer: () -> ImageLoader = {
        (PixnewsRootComponentHolder.appComponent as ImageLoaderProviderHolder).imageLoaderProvider.get()
    }
    private val imageLoader: ImageLoader by lazy(PUBLICATION) {
        imageLoaderInitializer()
    }

    @Composable
    internal fun getComposeImageLoader(): ImageLoader {
        return if (LocalInspectionMode.current) FakePreviewImageLoader else imageLoader
    }

    public fun overrideImageLoaderInitializer(initializer: () -> ImageLoader) {
        this.imageLoaderInitializer = initializer
    }
}

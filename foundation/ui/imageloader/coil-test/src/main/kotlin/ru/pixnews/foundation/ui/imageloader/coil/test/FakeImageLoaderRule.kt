/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil.test

import android.annotation.SuppressLint
import android.content.Context
import org.junit.rules.ExternalResource
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoaderHolder

public class FakeImageLoaderRule(context: () -> Context) : ExternalResource() {
    public val imageLoader: ImageLoader by lazy {
        FakeImageLoader.Builder(context()).build()
    }

    @SuppressLint("VisibleForTests")
    override fun before() {
        ImageLoaderHolder.overrideImageLoaderInitializer { imageLoader }
    }
}

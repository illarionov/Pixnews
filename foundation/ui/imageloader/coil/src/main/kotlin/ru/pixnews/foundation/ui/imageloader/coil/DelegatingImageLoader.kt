/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil

import coil.ComponentRegistry
import coil.ImageLoader.Builder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.DefaultRequestOptions
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult

internal class DelegatingImageLoader(
    private val rootImageLoader: coil.ImageLoader,
) : PrefetchingImageLoader, ImageLoader {
    override val components: ComponentRegistry get() = rootImageLoader.components
    override val defaults: DefaultRequestOptions get() = rootImageLoader.defaults
    override val diskCache: DiskCache? get() = rootImageLoader.diskCache
    override val memoryCache: MemoryCache? get() = rootImageLoader.memoryCache

    override fun enqueue(request: ImageRequest): Disposable = rootImageLoader.enqueue(request)

    override suspend fun execute(request: ImageRequest): ImageResult = rootImageLoader.execute(request)

    override fun newBuilder(): Builder {
        error("Creating new image loaders is supposed to be done via DI")
    }

    override fun shutdown() = rootImageLoader.shutdown()
}

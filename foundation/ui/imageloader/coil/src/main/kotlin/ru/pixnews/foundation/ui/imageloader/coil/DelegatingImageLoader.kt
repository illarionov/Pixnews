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

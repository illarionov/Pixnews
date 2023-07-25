/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil.test

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import coil.ComponentRegistry
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.disk.DiskCache.Editor
import coil.disk.DiskCache.Snapshot
import coil.memory.MemoryCache
import coil.request.DefaultRequestOptions
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.test.FakeImageLoaderEngine
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageUrlCoilInterceptor
import ru.pixnews.foundation.ui.imageloader.coil.PrefetchingImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.tooling.CoilDebugInterceptor
import java.io.File

public class FakeImageLoader private constructor(
    private val rootImageLoader: coil.ImageLoader,
) : ImageLoader, PrefetchingImageLoader {
    override val components: ComponentRegistry = rootImageLoader.components
    override val defaults: DefaultRequestOptions = rootImageLoader.defaults
    override val diskCache: DiskCache? = rootImageLoader.diskCache
    override val memoryCache: MemoryCache? = rootImageLoader.memoryCache

    override fun enqueue(request: ImageRequest): Disposable {
        return rootImageLoader.enqueue(request)
    }

    override suspend fun execute(request: ImageRequest): ImageResult {
        return rootImageLoader.execute(request)
    }

    override fun newBuilder(): coil.ImageLoader.Builder {
        throw UnsupportedOperationException()
    }

    override fun shutdown(): Unit = rootImageLoader.shutdown()

    @OptIn(ExperimentalCoilApi::class)
    public class Builder(
        private val context: Context,
    ) {
        private val engineBuilder: FakeImageLoaderEngine.Builder = FakeImageLoaderEngine.Builder()
            .default(ColorDrawable(Color.BLUE))

        public fun intercept(
            data: Any,
            drawable: Drawable,
        ): Builder = intercept({ it == data }, drawable)

        @Suppress("LAMBDA_IS_NOT_LAST_PARAMETER")
        public fun intercept(
            predicate: (data: Any) -> Boolean,
            drawable: Drawable,
        ): Builder {
            engineBuilder.intercept(predicate, drawable)
            return this
        }

        public fun default(drawable: Drawable): Builder {
            engineBuilder.default(drawable)
            return this
        }

        public fun build(): FakeImageLoader {
            val engine = engineBuilder.build()
            val imageLoader = coil.ImageLoader.Builder(context)
                .diskCache { DummyDiskCache() }
                .components {
                    add(CoilDebugInterceptor())
                    add(ImageUrlCoilInterceptor())
                    add(engine)
                }
                .build()
            return FakeImageLoader(imageLoader)
        }
    }

    private class DummyDiskCache(
        root: File = File("temp/coilCache"),
    ) : DiskCache {
        @ExperimentalCoilApi
        override val directory: Path = root.toOkioPath()

        @ExperimentalCoilApi
        override val fileSystem: FileSystem = FileSystem.SYSTEM

        @ExperimentalCoilApi
        override val maxSize: Long = 0

        @ExperimentalCoilApi
        override val size: Long = 0

        @ExperimentalCoilApi
        override fun clear() = Unit

        @ExperimentalCoilApi
        @Deprecated("")
        override fun edit(key: String): Editor? = null

        @ExperimentalCoilApi
        @Deprecated("")
        override fun get(key: String): Snapshot? = null

        @ExperimentalCoilApi
        override fun openEditor(key: String): Editor? = null

        @ExperimentalCoilApi
        override fun openSnapshot(key: String): Snapshot? = null

        @ExperimentalCoilApi
        override fun remove(key: String): Boolean = true
    }
}

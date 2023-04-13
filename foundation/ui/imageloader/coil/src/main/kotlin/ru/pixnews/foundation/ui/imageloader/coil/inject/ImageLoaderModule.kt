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
package ru.pixnews.foundation.ui.imageloader.coil.inject

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.core.app.ActivityManagerCompat
import androidx.core.content.getSystemService
import co.touchlab.kermit.Logger
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy.DISABLED
import coil.request.CachePolicy.ENABLED
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.MainCoroutineDispatcherProvider
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.network.RootOkHttpClientProvider
import ru.pixnews.foundation.ui.imageloader.coil.DelegatingImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoaderLogger
import ru.pixnews.foundation.ui.imageloader.coil.ImageUrlCoilInterceptor
import ru.pixnews.foundation.ui.imageloader.coil.PrefetchingImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.tooling.CoilDebugInterceptor
import ru.pixnews.library.android.utils.precondition.checkNotMainThread
import javax.inject.Provider
import javax.inject.Qualifier
import coil.ImageLoader as CoilImageLoader

private const val IMAGE_CACHE_SUBDIR = "image_cache"

@ContributesTo(AppScope::class)
@Module
@RestrictTo(LIBRARY)
public abstract class ImageLoaderModule {
    @Multibinds
    internal abstract fun provideCoilInterceptors(): DaggerSet<CoilInterceptorWithPriority>

    public companion object {
        @Provides
        @SingleIn(AppScope::class)
        public fun providesPrefetchingImageLoader(
            @RootImageLoader rootImageLoader: CoilImageLoader,
        ): PrefetchingImageLoader {
            return DelegatingImageLoader(rootImageLoader)
        }

        @Provides
        @SingleIn(AppScope::class)
        public fun providesUiImageLoader(
            @RootImageLoader rootImageLoader: CoilImageLoader,
            @Internal memoryCache: MemoryCache,
        ): ImageLoader {
            val imageLoaderWithMemoryCache = rootImageLoader
                .newBuilder()
                .memoryCache(memoryCache)
                .memoryCachePolicy(ENABLED)
                .build()
            return DelegatingImageLoader(imageLoaderWithMemoryCache)
        }

        @Provides
        @Internal
        @SingleIn(AppScope::class)
        public fun providesMemoryCache(
            @ApplicationContext context: Context,
        ): MemoryCache {
            return MemoryCache.Builder(context).build()
        }

        @Provides
        @RootImageLoader
        @SingleIn(AppScope::class)
        public fun providesRootImageLoader(
            @ApplicationContext context: Context,
            mainDispatcher: MainCoroutineDispatcherProvider,
            ioDispatcher: IoCoroutineDispatcherProvider,
            computationDispatcher: ComputationCoroutineDispatcherProvider,
            rootOkhttpClient: RootOkHttpClientProvider,
            @Internal diskCache: Provider<DiskCache>,
            interceptors: DaggerSet<CoilInterceptorWithPriority>,
            logger: Logger,
        ): CoilImageLoader {
            return CoilImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                    add(SvgDecoder.Factory())
                    add(VideoFrameDecoder.Factory())
                    interceptors
                        .toList()
                        .sortedBy(CoilInterceptorWithPriority::priority)
                        .forEach { add(it.interceptor) }
                }
                .decoderDispatcher(computationDispatcher.get())
                .allowRgb565(isLowRamDevice(context))
                .interceptorDispatcher(mainDispatcher.get().immediate)
                .fetcherDispatcher(ioDispatcher.get())
                .transformationDispatcher(computationDispatcher.get())
                .logger(ImageLoaderLogger(logger))
                .callFactory { request -> rootOkhttpClient.get().newCall(request) }
                .memoryCache(null)
                .memoryCachePolicy(DISABLED)
                .diskCache { diskCache.get() }
                .build()
        }

        @Internal
        @Provides
        @SingleIn(AppScope::class)
        @Suppress("MagicNumber")
        internal fun provideDiskCache(
            networkConfig: NetworkConfig,
            @ApplicationContext context: Context,
        ): DiskCache {
            checkNotMainThread()
            val cacheDir = context.externalCacheDir ?: context.cacheDir
            return DiskCache.Builder()
                .directory(cacheDir.resolve(IMAGE_CACHE_SUBDIR))
                .maxSizeBytes(networkConfig.imageCacheSizeMbytes.toLong() * 1_000_000L)
                .build()
        }

        @Provides
        @IntoSet
        internal fun provideImageUrlInterceptor(): CoilInterceptorWithPriority {
            return CoilInterceptorWithPriority(ImageUrlCoilInterceptor(), 0)
        }

        @Provides
        @ElementsIntoSet
        internal fun provideDebugImageUrlInterceptor(
            appConfig: AppConfig,
        ): DaggerSet<CoilInterceptorWithPriority> {
            return buildSet {
                if (appConfig.isDebug) {
                    @Suppress("MagicNumber")
                    add(CoilInterceptorWithPriority(CoilDebugInterceptor(), -10))
                }
            }
        }

        private fun isLowRamDevice(context: Context): Boolean {
            return context.getSystemService<ActivityManager>()?.let {
                ActivityManagerCompat.isLowRamDevice(it)
            } ?: true
        }

        @Qualifier
        internal annotation class RootImageLoader

        @Qualifier
        internal annotation class Internal
    }
}

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
package ru.pixnews.foundation.ui.imageloader.coil.di

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
import coil.memory.MemoryCache
import coil.request.CachePolicy.DISABLED
import coil.request.CachePolicy.ENABLED
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.dispatchers.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.MainCoroutineDispatcherProvider
import ru.pixnews.foundation.network.RootOkHttpClientProvider
import ru.pixnews.foundation.ui.imageloader.coil.DelegatingImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoaderLogger
import ru.pixnews.foundation.ui.imageloader.coil.PrefetchingImageLoader
import javax.inject.Qualifier

@ContributesTo(AppScope::class)
@Module
@RestrictTo(LIBRARY)
public object ImageLoaderModule {
    @Provides
    @SingleIn(AppScope::class)
    public fun providesPrefetchingImageLoader(
        @RootImageLoader rootImageLoader: coil.ImageLoader,
    ): PrefetchingImageLoader {
        return DelegatingImageLoader(rootImageLoader)
    }

    @Provides
    @SingleIn(AppScope::class)
    public fun providesUiImageLoader(
        @RootImageLoader rootImageLoader: coil.ImageLoader,
        @RootMemoryCache memoryCache: MemoryCache,
    ): ImageLoader {
        val imageLoaderWithMemoryCache = rootImageLoader
            .newBuilder()
            .memoryCache(memoryCache)
            .memoryCachePolicy(ENABLED)
            .build()
        return DelegatingImageLoader(imageLoaderWithMemoryCache)
    }

    @Provides
    @RootMemoryCache
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
        logger: Logger,
    ): coil.ImageLoader {
        return coil.ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(SvgDecoder.Factory())
                add(VideoFrameDecoder.Factory())
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
            .build()
    }

    private fun isLowRamDevice(context: Context): Boolean {
        return context.getSystemService<ActivityManager>()?.let {
            ActivityManagerCompat.isLowRamDevice(it)
        } ?: true
    }

    @Qualifier
    internal annotation class RootImageLoader

    @Qualifier
    internal annotation class RootMemoryCache
}

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
package ru.pixnews.foundation.ui.imageloader.coil.test

import android.content.Context
import org.junit.rules.ExternalResource
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoader
import ru.pixnews.foundation.ui.imageloader.coil.ImageLoaderHolder

public class FakeImageLoaderRule(context: () -> Context) : ExternalResource() {
    public val imageLoader: ImageLoader by lazy {
        FakeImageLoader.Builder(context()).build()
    }

    override fun before() {
        ImageLoaderHolder.overrideImageLoaderInitializer { imageLoader }
    }
}

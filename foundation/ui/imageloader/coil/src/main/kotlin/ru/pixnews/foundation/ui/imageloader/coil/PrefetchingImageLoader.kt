/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.imageloader.coil

import coil.ImageLoader

/**
 *  ImageLoader for preloading images only into the disk cache.
 *  Memory cache is disabled.
 */
public interface PrefetchingImageLoader : ImageLoader

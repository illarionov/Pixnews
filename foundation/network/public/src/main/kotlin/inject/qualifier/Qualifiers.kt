/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network.inject.qualifier

import javax.inject.Qualifier

@Qualifier
public annotation class RootHttpClientInterceptor

@Qualifier
public annotation class RootHttpClientNetworkInterceptor

@Qualifier
public annotation class RootHttpClientEventListener

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.base

/** Sugar over multibindings that helps with Kotlin wildcards. */
public typealias DaggerSet<T> = Set<@JvmSuppressWildcards T>

public typealias DaggerMap<K, V> = Map<@JvmSuppressWildcards K, @JvmSuppressWildcards V>

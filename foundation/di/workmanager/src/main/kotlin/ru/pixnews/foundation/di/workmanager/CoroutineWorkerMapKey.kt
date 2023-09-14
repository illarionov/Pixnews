/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.workmanager

import androidx.work.CoroutineWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
public annotation class CoroutineWorkerMapKey(val workerClass: KClass<out CoroutineWorker>)

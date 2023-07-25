/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext

public interface CoroutineWorkerFactory {
    public fun create(
        @ApplicationContext context: Context,
        workerParameters: WorkerParameters,
    ): CoroutineWorker
}

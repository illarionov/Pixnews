/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger

internal class PixnewsWorkerFactory(
    logger: Logger,
    private val workerSubcomponentFactory: WorkManagerSubcomponent.Factory,
) : WorkerFactory() {
    private val logger: Logger = logger.withTag("PixnewsWorkerFactory")

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        val workerComponent = workerSubcomponentFactory.create()
        val factory = workerComponent.workerFactories.firstNotNullOfOrNull {
            if (it.key.canonicalName == workerClassName) it.value else null
        }
        return if (factory != null) {
            factory.create(appContext, workerParameters)
        } else {
            logger.e { "No worker factory for workerClassName" }
            null
        }
    }
}

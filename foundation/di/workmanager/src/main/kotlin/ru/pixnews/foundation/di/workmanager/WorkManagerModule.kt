/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.workmanager

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.core.util.Consumer
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.workmanager.WorkManagerSubcomponent.Factory
import javax.inject.Qualifier

@ContributesTo(AppScope::class)
@Module
@RestrictTo(RestrictTo.Scope.LIBRARY)
public object WorkManagerModule {
    @Reusable
    @Provides
    public fun providesWorkMangerConfiguration(
        @InternalWorkerFactory workerFactory: WorkerFactory,
        appConfig: AppConfig,
        logger: Logger,
    ): Configuration {
        return with(Configuration.Builder()) {
            val loggingLevel = if (appConfig.isDebug) android.util.Log.DEBUG else android.util.Log.ERROR
            setMinimumLoggingLevel(loggingLevel)
            setWorkerFactory(workerFactory)
            if (!appConfig.isDebug) {
                setInitializationExceptionHandler(
                    Consumer {
                        logger.e("WorkManager initialization error", it)
                    },
                )
                setSchedulingExceptionHandler {
                    logger.e("WorkManager scheduling error", it)
                }
            }
            this.build()
        }
    }

    @Reusable
    @Provides
    public fun providesWorkManager(
        @ApplicationContext applicationContext: Context,
    ): WorkManager {
        return WorkManager.getInstance(applicationContext)
    }

    @Reusable
    @Provides
    @InternalWorkerFactory
    public fun providesWorkerFactory(
        logger: Logger,
        subcomponentFactory: Factory,
    ): WorkerFactory {
        return PixnewsWorkerFactory(logger, subcomponentFactory)
    }

    @Qualifier
    internal annotation class InternalWorkerFactory
}

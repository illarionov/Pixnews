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

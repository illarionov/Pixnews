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
package ru.pixnews.app

import android.content.Context
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import ru.pixnews.MainActivityComponent
import ru.pixnews.experiments.ExperimentsComponent
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.scopes.AppScope
import ru.pixnews.foundation.di.scopes.SingleIn
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.pub.FeatureManager

@MergeComponent(
    scope = AppScope::class,
    dependencies = [ExperimentsComponent::class],
)
@SingleIn(AppScope::class)
interface PixnewsAppComponent {
    fun mainActivityComponentFactory(): MainActivityComponent.Factory

    fun getIoDispatcherProvider(): IoCoroutineDispatcherProvider

    fun getLogger(): Logger

    fun getFeatureManager(): FeatureManager

    fun getAppConfig(): AppConfig

    @Component.Factory
    fun interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context,
            experimentsComponent: ExperimentsComponent,
        ): PixnewsAppComponent
    }

    public companion object {
        public operator fun invoke(
            @ApplicationContext context: Context,
            experimentsComponent: ExperimentsComponent,
        ): PixnewsAppComponent = DaggerPixnewsAppComponent.factory().create(context, experimentsComponent)
    }
}

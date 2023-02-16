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
package ru.pixnews.experiments

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.di.ExperimentScope

@Module
@ContributesTo(ExperimentScope::class)
public abstract class ExperimentsModule {
    @Multibinds
    public abstract fun appExperiments(): Set<@JvmSuppressWildcards Experiment>

    @Multibinds
    public abstract fun appExperimentSerializers(): @JvmSuppressWildcards Map<String, ExperimentVariantSerializer>

    companion object {
        @Provides
        @Reusable
        public fun provideAppExperimentVariantSerializers(
            serializers: @JvmSuppressWildcards Map<String, ExperimentVariantSerializer>,
        ): @JvmSuppressWildcards Map<ExperimentKey, ExperimentVariantSerializer> {
            return serializers
                .mapKeys { (k, _) -> ExperimentKey(k) }
        }
    }
}

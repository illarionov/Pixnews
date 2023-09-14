/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.experiments

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.inject.ExperimentScope

@Module
@ContributesTo(ExperimentScope::class)
public abstract class ExperimentsModule {
    @Multibinds
    public abstract fun appExperiments(): DaggerSet<Experiment>

    @Multibinds
    public abstract fun appExperimentSerializers(): DaggerMap<String, ExperimentVariantSerializer>

    companion object {
        @Provides
        @Reusable
        public fun provideAppExperimentVariantSerializers(
            serializers: DaggerMap<String, ExperimentVariantSerializer>,
        ): DaggerMap<ExperimentKey, ExperimentVariantSerializer> {
            return serializers
                .mapKeys { (k, _) -> ExperimentKey(k) }
        }
    }
}

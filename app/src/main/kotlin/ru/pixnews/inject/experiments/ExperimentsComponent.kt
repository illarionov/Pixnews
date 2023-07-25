/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject.experiments

import com.squareup.anvil.annotations.MergeComponent
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.inject.ExperimentScope

@SingleIn(ExperimentScope::class)
@MergeComponent(scope = ExperimentScope::class)
public interface ExperimentsComponent {
    public fun appExperiments(): DaggerSet<Experiment>

    @Suppress("MaxLineLength")
    public fun appExperimentVariantSerializers(): DaggerMap<ExperimentKey, ExperimentVariantSerializer>

    public companion object {
        public operator fun invoke(): ExperimentsComponent = DaggerExperimentsComponent.create()
    }
}

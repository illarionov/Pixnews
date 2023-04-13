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

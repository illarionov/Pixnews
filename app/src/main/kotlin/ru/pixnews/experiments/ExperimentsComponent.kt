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

import com.squareup.anvil.annotations.MergeComponent
import ru.pixnews.foundation.di.scopes.SingleIn
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.pub.di.ExperimentScope

@SingleIn(ExperimentScope::class)
@MergeComponent(scope = ExperimentScope::class)
public interface ExperimentsComponent {
    public fun appExperiments(): Set<@JvmSuppressWildcards Experiment>

    @Suppress("MaxLineLength")
    public fun appExperimentVariantSerializers(): Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>

    public companion object {
        public operator fun invoke(): ExperimentsComponent = DaggerExperimentsComponent.create()
    }
}

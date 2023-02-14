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
package ru.pixnews.features.featuretoggles.model

import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.experimentVariantKey

internal data class VariantUiModel constructor(
    val key: ExperimentVariantKey,
    val description: String = "",
) {
    internal companion object {
        internal operator fun invoke(key: String, description: String = ""): VariantUiModel =
            VariantUiModel(key.experimentVariantKey(), description)
    }
}

internal fun ExperimentVariant.toUiModel(): VariantUiModel {
    return VariantUiModel(
        key = this.key,
        description = this.description,
    )
}

internal fun VariantUiModel.toExperimentVariant(experiment: Experiment): ExperimentVariant {
    return experiment.variants[this.key] ?: error("No variant `$this` on experiment `$experiment`")
}

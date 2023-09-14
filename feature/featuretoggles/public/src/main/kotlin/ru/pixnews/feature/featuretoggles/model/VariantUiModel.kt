/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.model

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

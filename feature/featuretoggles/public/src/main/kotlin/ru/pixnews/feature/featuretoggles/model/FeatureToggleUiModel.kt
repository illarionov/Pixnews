/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.feature.featuretoggles.model.FeatureToggleState.ACTIVE
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.featuretoggles.getVariantKey

internal data class FeatureToggleUiModel(
    val key: ExperimentKey,
    val name: String,
    val description: String,
    val activeVariant: ExperimentVariantKey,
    val variants: ImmutableSet<VariantUiModel>,
    val isOverridden: Boolean = true,
    val uiState: FeatureToggleState = ACTIVE,
    val type: String = "Toggle",
)

internal enum class FeatureToggleState {
    UPDATING, ACTIVE
}

internal suspend fun FeatureToggle<Experiment>.toUiModel(): FeatureToggleUiModel {
    return FeatureToggleUiModel(
        key = experiment.key,
        name = experiment.name,
        description = experiment.description,
        activeVariant = this.getVariantKey(),
        variants = experiment.variants.map { (_, model) -> model.toUiModel() }.toImmutableSet(),
    )
}

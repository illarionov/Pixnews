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

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.features.featuretoggles.model.FeatureToggleState.ACTIVE
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.FeatureToggle
import ru.pixnews.foundation.featuretoggles.pub.getVariantKey

@Immutable
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

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
package ru.pixnews.features.featuretoggles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import co.touchlab.kermit.Logger
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.pixnews.features.featuretoggles.model.FeatureTogglesScreenState
import ru.pixnews.features.featuretoggles.model.PermanentErrorMessage
import ru.pixnews.features.featuretoggles.model.VariantUiModel
import ru.pixnews.features.featuretoggles.model.toExperimentVariant
import ru.pixnews.features.featuretoggles.model.toUiModel
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.FeatureManager
import ru.pixnews.foundation.featuretoggles.pub.FeatureToggle
import ru.pixnews.libraries.functional.RequestStatus.Complete
import ru.pixnews.libraries.functional.RequestStatus.Loading

internal class FeatureToggleListViewModel(
    featureManager: FeatureManager,
    private val overridesDataSource: OverridesDataSource,
    logger: Logger,
) : ViewModel() {
    @Suppress("UnusedPrivateMember")
    private val log = logger.withTag("FeatureTogglesViewModel")
    val viewState: StateFlow<FeatureTogglesScreenState> = overridesDataSource.getOverrides()
        .map {
            when (it) {
                is Loading -> FeatureTogglesScreenState.Loading
                is Complete -> getScreenContent(it.result)
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            FeatureTogglesScreenState.Loading,
        )
    private val togglesMap: Map<ExperimentKey, FeatureToggle<Experiment>> = featureManager.featureToggles.values
        .toList()
        .sortedWith(compareBy({ it.experiment.key.key }, { it.experiment.name }, { it.experiment.description }))
        .associateBy { it.experiment.key }

    private suspend fun getScreenContent(
        overridesResult: Either<Throwable, Map<ExperimentKey, ExperimentVariant>>,
    ): FeatureTogglesScreenState {
        return when (overridesResult) {
            is Left -> FeatureTogglesScreenState.PermanentError(PermanentErrorMessage(overridesResult.value))
            is Right -> getScreenContent(overridesResult.value)
        }
    }

    private suspend fun getScreenContent(
        overridesResult: Map<ExperimentKey, ExperimentVariant>,
    ): FeatureTogglesScreenState.Populated {
        val toggles = togglesMap.values.map { toggle ->
            toggle.toUiModel().copy(
                isOverridden = toggle.experiment.key in overridesResult,
            )
        }
        return FeatureTogglesScreenState.Populated(
            toggles.toImmutableList(),
        )
    }

    fun resetOverrides() {
        viewModelScope.launch {
            overridesDataSource.clearOverrides()
        }
    }

    fun onExperimentVariantSelected(experimentKey: ExperimentKey, variant: VariantUiModel) {
        viewModelScope.launch {
            val experiment = togglesMap[experimentKey]?.experiment
                ?: error("Experiment `$experimentKey` not in toggle list")
            overridesDataSource.setOverride(experimentKey, variant.toExperimentVariant(experiment))
        }
    }

    fun resetExperimentOverride(experimentKey: ExperimentKey) {
        viewModelScope.launch {
            overridesDataSource.setOverride(experimentKey, null)
        }
    }
}

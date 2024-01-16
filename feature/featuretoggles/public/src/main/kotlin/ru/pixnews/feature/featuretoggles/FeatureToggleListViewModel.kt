/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles

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
import ru.pixnews.anvil.codegen.viewmodel.inject.ContributesViewModel
import ru.pixnews.feature.featuretoggles.model.FeatureTogglesScreenState
import ru.pixnews.feature.featuretoggles.model.PermanentErrorMessage
import ru.pixnews.feature.featuretoggles.model.VariantUiModel
import ru.pixnews.feature.featuretoggles.model.toExperimentVariant
import ru.pixnews.feature.featuretoggles.model.toUiModel
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.functional.RequestStatus.Loading

@ContributesViewModel
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
        .sortedWith(compareBy({ it.experiment.key.stringValue }, { it.experiment.name }, { it.experiment.description }))
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

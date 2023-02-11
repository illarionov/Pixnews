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
package ru.pixnews.features.featuretoggles.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import ru.pixnews.features.featuretoggles.model.FeatureTogglesScreenState
import ru.pixnews.features.featuretoggles.model.FeatureTogglesScreenState.Loading
import ru.pixnews.features.featuretoggles.model.FeatureTogglesScreenState.PermanentError
import ru.pixnews.features.featuretoggles.model.FeatureTogglesScreenState.Populated
import ru.pixnews.features.featuretoggles.model.VariantUiModel
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey

@Suppress("ModifierMissing")
@Composable
internal fun FeatureToggleListScreen(
    viewStateFlow: StateFlow<FeatureTogglesScreenState>,
    onResetOverridesClicked: () -> Unit = {},
    onResetExperimentOverrideClicked: (ExperimentKey) -> Unit = {},
    onExperimentVariantSelected: (ExperimentKey, VariantUiModel) -> Unit = { _, _ -> },
) {
    val viewState by viewStateFlow.collectAsStateWithLifecycle()
    FeatureToggleListScreen(
        state = viewState,
        onResetOverridesClicked = onResetOverridesClicked,
        onResetExperimentOverrideClicked = onResetExperimentOverrideClicked,
        onExperimentVariantSelected = onExperimentVariantSelected,
    )
}

@VisibleForTesting
@Composable
internal fun FeatureToggleListScreen(
    state: FeatureTogglesScreenState,
    modifier: Modifier = Modifier,
    onResetOverridesClicked: () -> Unit = {},
    onResetExperimentOverrideClicked: (ExperimentKey) -> Unit = {},
    onExperimentVariantSelected: (ExperimentKey, VariantUiModel) -> Unit = { _, _ -> },
) {
    val containerColor = MaterialTheme.colorScheme.surfaceVariant
    val contentColor = contentColorFor(containerColor)
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                ToggleScreenTopAppBar(
                    showResetOverridesButton = state is Populated,
                    onResetOverridesClicked = onResetOverridesClicked,
                )
            },
        ) { paddingValues: PaddingValues ->
            when (state) {
                Loading -> LoadingScreen(paddingValues = paddingValues)
                is PermanentError -> PermanentErrorScreen(state.message, paddingValues = paddingValues)
                is Populated -> FeatureToggleListPopulated(
                    toggles = state.toggles,
                    paddingValues = paddingValues,
                    onResetExperimentOverrideClicked = onResetExperimentOverrideClicked,
                    onExperimentVariantSelected = onExperimentVariantSelected,
                )
            }
        }
    }
}

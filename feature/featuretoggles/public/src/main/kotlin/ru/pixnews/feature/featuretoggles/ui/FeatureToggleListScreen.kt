/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ru.pixnews.feature.featuretoggles.model.FeatureTogglesScreenState
import ru.pixnews.feature.featuretoggles.model.FeatureTogglesScreenState.Loading
import ru.pixnews.feature.featuretoggles.model.FeatureTogglesScreenState.PermanentError
import ru.pixnews.feature.featuretoggles.model.FeatureTogglesScreenState.Populated
import ru.pixnews.feature.featuretoggles.model.VariantUiModel
import ru.pixnews.foundation.featuretoggles.ExperimentKey

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
@ExperimentalMaterial3Api
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

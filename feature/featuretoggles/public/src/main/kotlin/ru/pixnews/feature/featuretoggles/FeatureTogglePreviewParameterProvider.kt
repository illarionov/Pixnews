/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.featuretoggles

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ru.pixnews.feature.featuretoggles.FeatureTogglePreviewFixtures.activeGroupUiModel
import ru.pixnews.feature.featuretoggles.FeatureTogglePreviewFixtures.controlGroupUiModel
import ru.pixnews.feature.featuretoggles.FeatureTogglePreviewFixtures.homeScreenGameCardModel
import ru.pixnews.feature.featuretoggles.model.FeatureToggleState.ACTIVE
import ru.pixnews.feature.featuretoggles.model.FeatureToggleState.UPDATING
import ru.pixnews.feature.featuretoggles.model.FeatureToggleUiModel
import ru.pixnews.feature.featuretoggles.model.VariantUiModel
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.experimentKey
import ru.pixnews.foundation.featuretoggles.experimentVariantKey

private const val FEATURE_TOGGLE_TYPE_TOGGLE = "Toggle"

internal object FeatureTogglePreviewFixtures {
    val controlGroupUiModel = VariantUiModel(
        key = CONTROL_GROUP,
        description = "Control (Default variant)",
    )
    val activeGroupUiModel = VariantUiModel(
        key = "active".experimentVariantKey(),
        description = "Experiment active",
    )
    val homeScreenGameCardModel = FeatureToggleUiModel(
        key = "feature.home_screen.game_card".experimentKey(),
        name = "Game card layout on home screen",
        description = "Feature flag to test different variants of the game card on the home screen",
        activeVariant = "v1".experimentVariantKey(),
        variants = persistentSetOf(
            controlGroupUiModel,
            VariantUiModel("v1", "Genres are shown using chips"),
            VariantUiModel("v2", "Genres at the bottom of the card"),
            VariantUiModel("v3", "With a \"more\" button"),
            VariantUiModel("v4", "With a display of gaming platforms"),
        ),
        uiState = UPDATING,
        type = FEATURE_TOGGLE_TYPE_TOGGLE,
    )
}

internal class FeatureTogglePreviewParameterProvider : PreviewParameterProvider<ImmutableList<FeatureToggleUiModel>> {
    override val values: Sequence<ImmutableList<FeatureToggleUiModel>>
        get() {
            val toggleList: ImmutableList<FeatureToggleUiModel> = persistentListOf(
                FeatureToggleUiModel(
                    key = "ui.dark_mode".experimentKey(),
                    name = "Dark mode",
                    description = "Feature flag for dark mode development",
                    activeVariant = "active".experimentVariantKey(),
                    variants = persistentSetOf(controlGroupUiModel, activeGroupUiModel),
                    isOverridden = true,
                    uiState = ACTIVE,
                    type = FEATURE_TOGGLE_TYPE_TOGGLE,
                ),

                FeatureToggleUiModel(
                    key = "feature.home_screen.header".experimentKey(),
                    name = "Header layout on home screen",
                    description = "Feature flag to test different variants of the header layout on the home screen",
                    activeVariant = controlGroupUiModel.key,
                    isOverridden = false,
                    variants = persistentSetOf(
                        controlGroupUiModel,
                        VariantUiModel("v1", ""),
                        VariantUiModel("v2", ""),
                        VariantUiModel("v3", ""),
                    ),
                    uiState = ACTIVE,
                    type = FEATURE_TOGGLE_TYPE_TOGGLE,
                ),
                homeScreenGameCardModel,
                FeatureToggleUiModel(
                    key = "integrations.dtf".experimentKey(),
                    name = "DTF Integration",
                    description = "Feature flag to develop DTF integration",
                    activeVariant = "control".experimentVariantKey(),
                    variants = persistentSetOf(
                        controlGroupUiModel,
                        activeGroupUiModel,
                    ),
                    uiState = UPDATING,
                    type = FEATURE_TOGGLE_TYPE_TOGGLE,
                ),
            )
            return sequenceOf(toggleList)
        }
}

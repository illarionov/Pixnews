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

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ru.pixnews.features.featuretoggles.FeatureTogglePreviewFixtures.activeGroupUiModel
import ru.pixnews.features.featuretoggles.FeatureTogglePreviewFixtures.controlGroupUiModel
import ru.pixnews.features.featuretoggles.FeatureTogglePreviewFixtures.homeScreenGameCardModel
import ru.pixnews.features.featuretoggles.model.FeatureToggleState.ACTIVE
import ru.pixnews.features.featuretoggles.model.FeatureToggleState.UPDATING
import ru.pixnews.features.featuretoggles.model.FeatureToggleUiModel
import ru.pixnews.features.featuretoggles.model.VariantUiModel
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

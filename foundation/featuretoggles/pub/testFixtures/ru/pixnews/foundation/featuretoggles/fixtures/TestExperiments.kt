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
package ru.pixnews.foundation.featuretoggles.fixtures

import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variants.ControlGroup
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variants.V1Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variants.V2Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variants.V3Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variants.V4Group
import ru.pixnews.foundation.featuretoggles.pub.DefaultExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.pub.experimentKey
import ru.pixnews.foundation.featuretoggles.pub.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.serializers.BooleanVariantSerializer
import ru.pixnews.foundation.featuretoggles.pub.serializers.StringVariantSerializer

public object DarkModeTestExperiment : Experiment {
    val controlGroup = DefaultExperimentVariant(
        key = CONTROL_GROUP,
        description = "Dark mode is not active",
        weight = 1,
    )
    val activeGroup = DefaultExperimentVariant(
        key = "active".experimentVariantKey(),
        description = "Dark mode is active",
        weight = 0,
    )
    override val key: ExperimentKey = "ui.dark_mode".experimentKey()
    override val name: String = "Dark mode"
    override val description: String = "Feature flag for dark mode development"
    override val variants = listOf(controlGroup, activeGroup)
        .associateBy(ExperimentVariant::key)

    object Serializer : BooleanVariantSerializer(
        controlGroupVariant = DarkModeTestExperiment.controlGroup,
        activeGroupVariant = DarkModeTestExperiment.activeGroup,
    )
}

public object HomeScreenGameCardTestExperiment : Experiment {
    override val key: ExperimentKey = "feature.home_screen.game_card".experimentKey()
    override val name: String = "Game card layout on home screen"
    override val description: String = "Feature flag to test different variants of the game card on the home screen"
    override val variants = listOf(ControlGroup, V1Group, V2Group, V3Group, V4Group)
        .associateBy(ExperimentVariant::key)

    object Serializer : StringVariantSerializer(variants.mapKeys { it.key.key })
    sealed class Variants : ExperimentVariant {
        object ControlGroup : Variants() {
            override val key = CONTROL_GROUP
            override val description = "Default Game Card on home screen"
        }
        object V1Group : Variants() {
            override val key = "v1".experimentVariantKey()
            override val description = "Genres are shown using chips"
        }
        object V2Group : Variants() {
            override val key = "v2".experimentVariantKey()
            override val description = "Genres at the bottom of the card"
        }
        object V3Group : Variants() {
            override val key = "v3".experimentVariantKey()
            override val description = "With a \"more\" button"
        }
        object V4Group : Variants() {
            override val key = "v4".experimentVariantKey()
            override val description = "With a display of gaming platforms"
        }
    }
}

public object DtfIntegrationTestExperiment : Experiment {
    val controlGroup: ExperimentVariant = DefaultExperimentVariant(
        key = CONTROL_GROUP,
        description = "DTF Integration is not active",
    )
    val activeGroup: ExperimentVariant = DefaultExperimentVariant(
        key = "active".experimentVariantKey(),
        description = "DTF Integration is active",
    )
    override val key: ExperimentKey = "integrations.dtf".experimentKey()
    override val name: String = "DTF Integration"
    override val description = "Feature flag to develop DTF integration"
    override val variants = listOf(controlGroup, activeGroup).associateBy(ExperimentVariant::key)

    object Serializer : BooleanVariantSerializer(
        controlGroupVariant = DtfIntegrationTestExperiment.controlGroup,
        activeGroupVariant = DtfIntegrationTestExperiment.activeGroup,
    )
}

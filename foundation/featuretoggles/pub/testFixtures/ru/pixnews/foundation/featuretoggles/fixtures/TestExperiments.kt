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

import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variant.ControlGroup
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variant.V1Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variant.V2Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variant.V3Group
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment.Variant.V4Group
import ru.pixnews.foundation.featuretoggles.pub.DefaultExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.pub.experimentKey
import ru.pixnews.foundation.featuretoggles.pub.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.serializers.BooleanVariantSerializer
import ru.pixnews.foundation.featuretoggles.pub.serializers.StringVariantSerializer

public object DarkModeTestExperiment : Experiment {
    public val controlGroup: ExperimentVariant = DefaultExperimentVariant(
        key = CONTROL_GROUP,
        description = "Dark mode is not active",
        weight = 1,
    )
    public val activeGroup: ExperimentVariant = DefaultExperimentVariant(
        key = "active".experimentVariantKey(),
        description = "Dark mode is active",
        weight = 0,
    )
    override val key: ExperimentKey = "ui.dark_mode".experimentKey()
    override val name: String = "Dark mode"
    override val description: String = "Feature flag for dark mode development"
    override val variants: Map<ExperimentVariantKey, ExperimentVariant> = listOf(controlGroup, activeGroup)
        .associateBy(ExperimentVariant::key)

    public object Serializer : BooleanVariantSerializer(
        controlGroupVariant = controlGroup,
        activeGroupVariant = activeGroup,
    )
}

public object HomeScreenGameCardTestExperiment : Experiment {
    override val key: ExperimentKey = "feature.home_screen.game_card".experimentKey()
    override val name: String = "Game card layout on home screen"
    override val description: String = "Feature flag to test different variants of the game card on the home screen"
    override val variants: Map<ExperimentVariantKey, Variant> = listOf(ControlGroup, V1Group, V2Group, V3Group, V4Group)
        .associateBy(ExperimentVariant::key)

    public object Serializer : StringVariantSerializer(variants.mapKeys { it.key.key })
    public sealed class Variant : ExperimentVariant {
        public object ControlGroup : Variant() {
            override val key: ExperimentVariantKey = CONTROL_GROUP
            override val description: String = "Default Game Card on home screen"
        }
        public object V1Group : Variant() {
            override val key: ExperimentVariantKey = "v1".experimentVariantKey()
            override val description: String = "Genres are shown using chips"
        }
        public object V2Group : Variant() {
            override val key: ExperimentVariantKey = "v2".experimentVariantKey()
            override val description: String = "Genres at the bottom of the card"
        }
        public object V3Group : Variant() {
            override val key: ExperimentVariantKey = "v3".experimentVariantKey()
            override val description: String = "With a \"more\" button"
        }
        public object V4Group : Variant() {
            override val key: ExperimentVariantKey = "v4".experimentVariantKey()
            override val description: String = "With a display of gaming platforms"
        }
    }
}

public object DtfIntegrationTestExperiment : Experiment {
    public val controlGroup: ExperimentVariant = DefaultExperimentVariant(
        key = CONTROL_GROUP,
        description = "DTF Integration is not active",
    )
    public val activeGroup: ExperimentVariant = DefaultExperimentVariant(
        key = "active".experimentVariantKey(),
        description = "DTF Integration is active",
    )
    override val key: ExperimentKey = "integrations.dtf".experimentKey()
    override val name: String = "DTF Integration"
    override val description: String = "Feature flag to develop DTF integration"
    override val variants: Map<ExperimentVariantKey, ExperimentVariant> = listOf(controlGroup, activeGroup)
        .associateBy(ExperimentVariant::key)

    public object Serializer : BooleanVariantSerializer(
        controlGroupVariant = controlGroup,
        activeGroupVariant = activeGroup,
    )
}

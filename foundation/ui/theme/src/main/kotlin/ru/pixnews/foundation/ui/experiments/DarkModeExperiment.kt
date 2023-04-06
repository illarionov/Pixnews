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
package ru.pixnews.foundation.ui.experiments

import com.squareup.anvil.annotations.ContributesMultibinding
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.experimentKey
import ru.pixnews.foundation.featuretoggles.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.inject.ExperimentScope
import ru.pixnews.foundation.featuretoggles.inject.ExperimentVariantMapKey
import ru.pixnews.foundation.featuretoggles.serializers.BooleanVariantSerializer

@ContributesMultibinding(
    scope = ExperimentScope::class,
    boundType = Experiment::class,
)
public object DarkModeExperiment : Experiment {
    override val key: ExperimentKey = "ui.dark_mode".experimentKey()
    override val name: String = "Dark mode"
    override val description: String = "Feature flag for dark mode development"
    override val variants: Map<ExperimentVariantKey, Variant> = listOf(Variant.Control, Variant.Active)
        .associateBy(ExperimentVariant::key)

    @ContributesMultibinding(scope = ExperimentScope::class, boundType = ExperimentVariantSerializer::class)
    @ExperimentVariantMapKey("ui.dark_mode")
    public object Serializer : BooleanVariantSerializer(Variant.Control, Variant.Active)

    public sealed class Variant : ExperimentVariant {
        public object Control : Variant() {
            override val key: ExperimentVariantKey = CONTROL_GROUP
            override val description: String = "Dark mode is not active"
        }

        public object Active : Variant() {
            override val key: ExperimentVariantKey = "active".experimentVariantKey()
            override val description: String = "Dark mode is active"
        }
    }
}

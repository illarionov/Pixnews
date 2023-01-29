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
import ru.pixnews.foundation.featuretoggles.pub.DefaultExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.pub.di.ExperimentScope
import ru.pixnews.foundation.featuretoggles.pub.di.ExperimentVariantMapKey
import ru.pixnews.foundation.featuretoggles.pub.experimentKey
import ru.pixnews.foundation.featuretoggles.pub.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.pub.serializers.BooleanVariantSerializer

private const val DARK_MODE_EXPERIMENT_KEY: String = "dark_mode"
private val controlGroup = DefaultExperimentVariant(
    key = CONTROL_GROUP,
    description = "Dark mode is not active",
    weight = 1,
)
private val activeGroup = DefaultExperimentVariant(
    key = "active".experimentVariantKey(),
    description = "Dark mode is active",
    weight = 0,
)

@ContributesMultibinding(
    scope = ExperimentScope::class,
    boundType = Experiment::class,
)
public object DarkModeExperiment : Experiment {
    override val key: ExperimentKey = DARK_MODE_EXPERIMENT_KEY.experimentKey()
    override val name: String = "Dark mode"
    override val description: String = "Feature flag for dark mode development"
    override val variants: Map<ExperimentVariantKey, DefaultExperimentVariant> = listOf(controlGroup, activeGroup)
        .associateBy(ExperimentVariant::key)
}

@ContributesMultibinding(
    scope = ExperimentScope::class,
    boundType = ExperimentVariantSerializer::class,
)
@ExperimentVariantMapKey(DARK_MODE_EXPERIMENT_KEY)
public object DarkModeExperimentSerializer : BooleanVariantSerializer(controlGroup, activeGroup)

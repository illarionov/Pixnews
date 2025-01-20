/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.experiments

import ru.pixnews.anvil.ksp.codegen.experiment.inject.ContributesExperiment
import ru.pixnews.anvil.ksp.codegen.experiment.inject.ContributesExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.experimentKey
import ru.pixnews.foundation.featuretoggles.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.serializers.BooleanVariantSerializer

@ContributesExperiment
public object DarkModeExperiment : Experiment {
    override val key: ExperimentKey = "ui.dark_mode".experimentKey()
    override val name: String = "Dark mode"
    override val description: String = "Feature flag for dark mode development"
    override val variants: Map<ExperimentVariantKey, Variant> = listOf(Variant.Control, Variant.Active)
        .associateBy(ExperimentVariant::key)

    @ContributesExperimentVariantSerializer("ui.dark_mode")
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

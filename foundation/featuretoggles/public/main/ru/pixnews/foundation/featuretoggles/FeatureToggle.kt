/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles

public interface FeatureToggle<out E : Experiment> {
    public val experiment: E

    public suspend fun <V : ExperimentVariant> getVariant(): V
}

public suspend fun FeatureToggle<*>.getVariantKey(): ExperimentVariantKey = getVariant<ExperimentVariant>().key

public suspend fun FeatureToggle<*>.isEnabled(): Boolean = getVariant<ExperimentVariant>().key.isControl()

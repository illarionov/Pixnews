/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles

public interface FeatureManager {
    public val featureToggles: Map<ExperimentKey, FeatureToggle<*>>

    public suspend fun suspendUntilLoaded()
}

@Suppress("UNCHECKED_CAST")
public fun <E : Experiment, FT : FeatureToggle<E>> FeatureManager.getFeatureToggle(
    key: ExperimentKey,
): FT = ((featureToggles[key] ?: throw NoSuchElementException("Experiment $key not defined")) as FT)

public fun <E : Experiment, FT : FeatureToggle<E>> FeatureManager.getFeatureToggle(
    key: String,
): FT = getFeatureToggle(ExperimentKey(key))

public fun <E : Experiment, FT : FeatureToggle<E>> FeatureManager.getFeatureToggle(
    experiment: E,
): FT = getFeatureToggle(experiment.key)

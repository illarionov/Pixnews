/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles

public interface Experiment {
    /**
     * Unique key of the experiment.
     */
    public val key: ExperimentKey

    /**
     * Name of the experiment to be shown in the debug panel. May be empty.
     */
    public val name: String

    /**
     * Description of the experiment to be shown in the debug panel. May be empty.
     */
    public val description: String

    /**
     * Variants of the experiment.
     * Should contain at least one variant.
     */
    public val variants: Map<ExperimentVariantKey, ExperimentVariant>
}

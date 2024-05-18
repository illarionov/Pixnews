/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles

public interface ExperimentVariant {
    public val key: ExperimentVariantKey

    /**
     * Description of the variant to be shown in the debug panel. May be empty.
     */
    public val description: String
        get() = ""
    public val weight: Int
        get() = 0
    public val payload: String?
        get() = null
}

public data class DefaultExperimentVariant(
    override val key: ExperimentVariantKey,
    override val description: String = "",
    override val weight: Int = 1,
    override val payload: String? = null,
) : ExperimentVariant

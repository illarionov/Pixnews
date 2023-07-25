/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles

/**
 * Name of the the variant. Must be unique among the variants of the experiment.
 * Usually the name for the group of users who will get access to this variant.
 */
@JvmInline
public value class ExperimentVariantKey(public val stringValue: String) {
    init {
        require(stringValue.matches(KEY_VARIANT_FORMAT_REGEX))
    }

    override fun toString(): String = stringValue

    public companion object {
        private val KEY_VARIANT_FORMAT_REGEX: Regex = """[a-z0-9._-]+""".toRegex()

        /**
         * Unique name for the control group.
         *
         */
        public val CONTROL_GROUP: ExperimentVariantKey = ExperimentVariantKey("control")
    }
}

public fun ExperimentVariantKey.isControl(): Boolean = this == ExperimentVariantKey.CONTROL_GROUP

public fun String.experimentVariantKey(): ExperimentVariantKey = ExperimentVariantKey(this)

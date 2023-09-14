/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles

@JvmInline
public value class ExperimentKey(public val stringValue: String) {
    init {
        require(stringValue.matches(KEY_FORMAT_REGEX)) {
            "Experiment key `$stringValue` should match experiment key format"
        }
    }

    override fun toString(): String = stringValue

    private companion object {
        private val KEY_FORMAT_REGEX: Regex = """[a-z0-9._-]+""".toRegex()
    }
}
public fun String.experimentKey(): ExperimentKey = ExperimentKey(this)

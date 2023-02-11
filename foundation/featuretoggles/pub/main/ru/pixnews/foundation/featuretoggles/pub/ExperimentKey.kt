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
package ru.pixnews.foundation.featuretoggles.pub

@JvmInline
public value class ExperimentKey(public val key: String) {
    init {
        require(key.matches(KEY_FORMAT_REGEX)) { "Experiment key `$key` should match experiment key format" }
    }

    private companion object {
        private val KEY_FORMAT_REGEX: Regex = """[a-z0-9._-]+""".toRegex()
    }
}
public fun String.experimentKey(): ExperimentKey = ExperimentKey(this)

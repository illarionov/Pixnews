/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed class FeatureTogglesScreenState {
    internal object Loading : FeatureTogglesScreenState() {
        override fun toString(): String = "Loading"
    }

    internal data class PermanentError(
        val message: PermanentErrorMessage,
    ) : FeatureTogglesScreenState()

    internal data class Populated(
        val toggles: ImmutableList<FeatureToggleUiModel>,
    ) : FeatureTogglesScreenState()
}

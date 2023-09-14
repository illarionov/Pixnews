/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.ui.tooling.debuglayout

internal data class DisplayMetrics(val density: Float, val xdpi: Float, val ydpi: Float) {
    public fun withDensity(density: Float): DisplayMetrics {
        return if (this.density != density) {
            this.copy(density = density)
        } else {
            this
        }
    }
}

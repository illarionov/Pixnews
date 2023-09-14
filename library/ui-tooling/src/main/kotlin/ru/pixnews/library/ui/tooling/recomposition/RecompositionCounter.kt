/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.ui.tooling.recomposition

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.platform.debugInspectorInfo
import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay

// Original author: https://github.com/AidaIssayeva/recomposition_examples

private const val RECOMPOSITION_COUNTER_TIMEOUT: Long = 3000L

@Stable
public fun Modifier.recompositionCounter(callingName: String): Modifier = this.then(
    Modifier.composed(
        inspectorInfo = debugInspectorInfo { name = "recompositionCounter: + $callingName" },
    ) {
        // The total number of compositions that have occurred. We're not using a State<> here be
        // able to read/write the value without invalidating (which would cause infinite
        // recomposition).
        val totalCompositions = remember { arrayOf(0L) }
        totalCompositions[0]++

        // The value of totalCompositions at the last timeout.
        val totalCompositionsAtLastTimeout = remember { mutableStateOf(0L) }

        // Start the timeout, and reset everytime there's a recomposition. (Using totalCompositions
        // as the key is really just to cause the timer to restart every composition).
        LaunchedEffect(totalCompositions[0]) {
            delay(RECOMPOSITION_COUNTER_TIMEOUT)
            totalCompositionsAtLastTimeout.value = totalCompositions[0]
        }

        Modifier.drawWithCache {
            onDrawWithContent {
                // Below is to draw the highlight, if necessary. A lot of the logic is copied from
                // Modifier.border
                val numCompositionsSinceTimeout =
                    totalCompositions[0] - totalCompositionsAtLastTimeout.value
                if (numCompositionsSinceTimeout > 0) {
                    Logger.i("recompositionCounter") { "$callingName - $numCompositionsSinceTimeout" }
                }

                // Draw actual content.
                drawContent()
            }
        }
    },
)

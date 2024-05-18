/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.printToString
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import co.touchlab.kermit.Logger
import radiography.Radiography
import radiography.ViewStateRenderers
import java.io.ByteArrayOutputStream

public object HierarchyDumper {
    private const val TAG: String = "Hierarchy"

    public fun printAllToLog() {
        Logger.i(TAG) {
            printAllToString()
        }
    }

    public fun printToLog(
        interaction: SemanticsNodeInteraction,
        maxDepth: Int = Int.MAX_VALUE,
    ) {
        Logger.i(TAG) {
            "Hierarchy:\n${interaction.printToString(maxDepth)}"
        }
    }

    public fun printAllToString(): String {
        val prettyHierarchy = Radiography.scan(
            viewStateRenderers = ViewStateRenderers.DefaultsIncludingPii,
        )
        return "\nView hierarchies:\n$prettyHierarchy"
    }

    public fun printXmlWindowHierarchyToLog() {
        Logger.i(TAG) {
            printXmlWindowHierarchyToString()
        }
    }

    public fun printXmlWindowHierarchyToString(): String {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val outputStream = ByteArrayOutputStream()
        device.dumpWindowHierarchy(outputStream)
        return "\nWindow hierarchy:\n$outputStream"
    }
}

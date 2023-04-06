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
package ru.pixnews.foundation.instrumented.test.util

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

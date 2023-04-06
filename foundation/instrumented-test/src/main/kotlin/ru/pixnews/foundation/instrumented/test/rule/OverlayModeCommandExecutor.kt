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
package ru.pixnews.foundation.instrumented.test.rule

import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode.GESTURAL
import ru.pixnews.foundation.instrumented.test.rule.SystemNavigationRule.NavigationMode.THREE_BUTTON
import java.io.InputStream

internal class OverlayModeCommandExecutor(
    private val shellCommandExecutor: (String) -> InputStream,
) {
    public fun getGesturalMode(): NavigationMode {
        return getOverlayInfoByPackageOrTarget(GESTURAL_NAVBAR_PACKAGE_NAME).findNavigationModeOutput()
    }

    public fun setGesturalMode(mode: NavigationMode): NavigationMode {
        val subCommand = when (mode) {
            GESTURAL -> "enable"
            else -> "disable"
        }

        val command = "cmd overlay $subCommand $GESTURAL_NAVBAR_PACKAGE_NAME"
        return shellCommandExecutor.invoke(command)
            .bufferedReader()
            .readLines()
            .map {
                it.parseBriefOverlayInfo()
                    ?: throw ParseException("Can not parse `cmd ` output: `$it`")
            }
            .findNavigationModeOutput()
    }

    private fun getOverlayInfoByPackageOrTarget(pkg: String): Iterable<OverlayInfo> {
        val cmd = "cmd overlay list $pkg"
        return shellCommandExecutor.invoke(cmd)
            .bufferedReader()
            .readLines()
            .mapNotNull {
                if (it == pkg) {
                    return@mapNotNull null
                }
                it.parseBriefOverlayInfo()
                    ?: throw ParseException("Can not parse `$cmd` output: `$it`")
            }.toList()
    }

    private fun Iterable<OverlayInfo>.findNavigationModeOutput(): NavigationMode {
        return this
            .firstOrNull { it.packageName == GESTURAL_NAVBAR_PACKAGE_NAME }
            .let { gesturalOverlayInfo ->
                if (gesturalOverlayInfo != null && gesturalOverlayInfo.isEnabled) {
                    GESTURAL
                } else {
                    THREE_BUTTON
                }
            }
    }

    private fun String.parseBriefOverlayInfo(): OverlayInfo? {
        val isEnabledTarget = CMD_OVERLAY_LIST_ITEM_REGEX.matchEntire(this)?.groupValues
            ?: return null

        @Suppress("MagicNumber")
        check(isEnabledTarget.size == 3)
        val isEnabled = when (isEnabledTarget[1]) {
            "x" -> true
            " " -> false
            else -> return null
        }
        return OverlayInfo(isEnabledTarget[2], isEnabled)
    }

    private class ParseException(message: String) : RuntimeException(message)

    private data class OverlayInfo(
        val packageName: String,
        val isEnabled: Boolean,
    )

    private companion object {
        private const val GESTURAL_NAVBAR_PACKAGE_NAME = "com.android.internal.systemui.navbar.gestural"
        private val CMD_OVERLAY_LIST_ITEM_REGEX = """\[([ x])] (\S+)""".toRegex()
    }
}

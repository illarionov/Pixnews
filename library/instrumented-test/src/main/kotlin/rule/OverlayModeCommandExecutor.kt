/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.rule

import ru.pixnews.library.instrumented.test.rule.SystemNavigationRule.NavigationMode
import ru.pixnews.library.instrumented.test.rule.SystemNavigationRule.NavigationMode.GESTURAL
import ru.pixnews.library.instrumented.test.rule.SystemNavigationRule.NavigationMode.THREE_BUTTON
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

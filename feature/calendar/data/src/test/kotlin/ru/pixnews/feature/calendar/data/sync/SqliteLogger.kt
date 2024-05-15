/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.sync

import co.touchlab.kermit.Logger
import ru.pixnews.wasm.sqlite.open.helper.common.api.Logger as WasmHelperLogger

@Suppress("IDENTIFIER_LENGTH")
internal class SqliteLogger(
    private val kermitLogger: Logger,
) : WasmHelperLogger {
    override fun a(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.a(throwable, message)
        } else {
            kermitLogger.a(message)
        }
    }

    override fun d(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.d(throwable, message)
        } else {
            kermitLogger.d(message)
        }
    }

    override fun e(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.e(throwable, message)
        } else {
            kermitLogger.e(message)
        }
    }

    override fun i(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.i(throwable, message)
        } else {
            kermitLogger.i(message)
        }
    }

    override fun v(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.v(throwable, message)
        } else {
            kermitLogger.v(message)
        }
    }

    override fun w(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            kermitLogger.w(throwable, message)
        } else {
            kermitLogger.w(message)
        }
    }

    override fun withTag(tag: String): WasmHelperLogger {
        return SqliteLogger(kermitLogger.withTag(tag))
    }
}

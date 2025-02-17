/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.util.strictmode

import android.os.strictmode.Violation

class IgnoredViolation(
    val name: String,
    val predicate: Violation.() -> Boolean,
)

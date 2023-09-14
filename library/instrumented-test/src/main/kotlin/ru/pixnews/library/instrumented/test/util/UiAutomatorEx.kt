/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.util

import android.app.UiAutomation
import android.os.ParcelFileDescriptor.AutoCloseInputStream
import java.io.InputStream

public fun UiAutomation.executeShellCommandIs(command: String): InputStream {
    return AutoCloseInputStream(this.executeShellCommand(command))
}

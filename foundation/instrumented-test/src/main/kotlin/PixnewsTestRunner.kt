/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.instrumented.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

public class PixnewsTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context,
    ): Application {
        return super.newApplication(cl, PixnewsTestApplication::class.java.name, context)
    }
}

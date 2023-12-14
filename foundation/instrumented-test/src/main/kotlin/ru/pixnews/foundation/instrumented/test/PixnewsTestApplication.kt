/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.instrumented.test

import android.app.Application
import androidx.work.Configuration

public class PixnewsTestApplication : Application(), Configuration.Provider {
    private val _workManagerConfiguration = Configuration.Builder().build()
    override val workManagerConfiguration: Configuration = _workManagerConfiguration

    override fun onCreate() {
        super.onCreate()
    }
}

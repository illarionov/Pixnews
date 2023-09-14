/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.activity

import android.app.Activity
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY)
public fun interface ActivityInjector {
    public fun inject(activity: Activity)
}

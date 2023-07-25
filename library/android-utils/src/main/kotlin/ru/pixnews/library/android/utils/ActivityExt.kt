/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.android.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup

public val Activity.rootView: View
    get() = window.decorView.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)

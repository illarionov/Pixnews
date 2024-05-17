/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.espresso

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.base.DefaultFailureHandler
import org.hamcrest.Matcher
import ru.pixnews.library.instrumented.test.util.HierarchyDumper

public class CleanHierarchyEspressoFailureHandler(
    targetApplicationContext: Context,
) : FailureHandler {
    private val delegate: FailureHandler

    init {
        delegate = DefaultFailureHandler(targetApplicationContext)
    }

    @Suppress("TooGenericExceptionCaught")
    @SuppressLint("DiscouragedPrivateApi")
    override fun handle(error: Throwable?, viewMatcher: Matcher<View>?) {
        try {
            delegate.handle(error, viewMatcher)
        } catch (decoratedError: Throwable) {
            val detailMessageField = Throwable::class.java.getDeclaredField("detailMessage")
            val previousAccessible = detailMessageField.isAccessible
            try {
                detailMessageField.isAccessible = true
                var message = (detailMessageField[decoratedError] as String?).orEmpty()
                message = message.substringBefore("\nView Hierarchy:")
                message += HierarchyDumper.printAllToString()
                detailMessageField[decoratedError] = message
            } finally {
                detailMessageField.isAccessible = previousAccessible
            }
            throw decoratedError
        }
    }
}

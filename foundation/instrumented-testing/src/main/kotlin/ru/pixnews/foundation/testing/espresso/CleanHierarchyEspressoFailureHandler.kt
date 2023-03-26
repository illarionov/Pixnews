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
package ru.pixnews.foundation.testing.espresso

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.base.DefaultFailureHandler
import org.hamcrest.Matcher
import ru.pixnews.foundation.testing.util.HierarchyDumper

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

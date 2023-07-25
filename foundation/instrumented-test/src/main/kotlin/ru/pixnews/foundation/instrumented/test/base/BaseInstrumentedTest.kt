/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.instrumented.test.base

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import org.junit.Before
import ru.pixnews.library.instrumented.test.espresso.CleanHierarchyEspressoFailureHandler

public abstract class BaseInstrumentedTest {
    @Before
    public open fun setupEspresso() {
        Espresso.setFailureHandler(
            CleanHierarchyEspressoFailureHandler(ApplicationProvider.getApplicationContext()),
        )
    }
}

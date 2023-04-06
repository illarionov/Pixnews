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
package ru.pixnews.foundation.instrumented.test.base

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import org.junit.Before
import ru.pixnews.foundation.instrumented.test.espresso.CleanHierarchyEspressoFailureHandler

public abstract class BaseInstrumentedTest {
    @Before
    public open fun setupEspresso() {
        Espresso.setFailureHandler(
            CleanHierarchyEspressoFailureHandler(ApplicationProvider.getApplicationContext()),
        )
    }
}

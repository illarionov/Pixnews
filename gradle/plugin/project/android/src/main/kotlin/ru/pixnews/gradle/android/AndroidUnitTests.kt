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
package ru.pixnews.gradle.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import ru.pixnews.gradle.base.UnitTestEngine
import ru.pixnews.gradle.testing.configureCommonUnitTesting
import ru.pixnews.gradle.testing.configureCommonUnitTestingOptions

internal fun Project.configureUnitTesting(
    engine: UnitTestEngine,
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    configureCommonUnitTesting(engine)
    commonExtension.testOptions {
        unitTests.all { testExtension ->
            testExtension.configureCommonUnitTestingOptions(engine)
        }
    }
}
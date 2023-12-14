/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import ru.pixnews.gradle.base.UnitTestEngine
import ru.pixnews.gradle.testing.configureCommonUnitTesting
import ru.pixnews.gradle.testing.configureCommonUnitTestingOptions

internal fun Project.configureUnitTesting(
    engine: UnitTestEngine,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    configureCommonUnitTesting(engine)
    commonExtension.testOptions {
        unitTests.all { testExtension ->
            testExtension.configureCommonUnitTestingOptions(engine)
        }
    }
}

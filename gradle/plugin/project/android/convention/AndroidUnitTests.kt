/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import ru.pixnews.gradle.project.base.UnitTestEngine
import ru.pixnews.gradle.project.testing.configureCommonUnitTesting
import ru.pixnews.gradle.project.testing.configureCommonUnitTestingOptions

internal fun Project.configureUnitTesting(
    engine: UnitTestEngine,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    configureCommonUnitTesting(engine)
    commonExtension.testOptions {
        unitTests.all { testTask: Test ->
            testTask.configureCommonUnitTestingOptions(engine)
        }
    }
}

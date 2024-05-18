/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.android

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.TestedExtension
import org.gradle.api.Project
import ru.pixnews.gradle.project.base.PixnewsExtension

internal fun PixnewsExtension.applyTo(project: Project, commonExtension: CommonExtension<*, *, *, *, *, *>) {
    if (compose.get()) {
        project.configureCompose(commonExtension)
    }
    if (commonExtension is TestedExtension) {
        project.configureUnitTesting(unitTestEngine.get(), commonExtension)
    }
}

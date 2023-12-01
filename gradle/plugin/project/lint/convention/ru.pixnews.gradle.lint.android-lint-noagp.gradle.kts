/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.Lint
import com.android.build.gradle.LintPlugin
import ru.pixnews.gradle.lint.configureCommonAndroidLint

/**
 * Convention plugin that configures Android Lint in libraries without the Android Gradle plugin
 */
apply<LintPlugin>()

extensions.configure<Lint> {
    configureCommonAndroidLint()
}

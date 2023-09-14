/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.gradle.lint.configRootDir
import ru.pixnews.gradle.lint.excludeNonLintedDirectories

/**
 * Convention plugin that configures Diktat
 */
plugins {
    id("org.cqfn.diktat.diktat-gradle-plugin")
}

diktat {
    diktatConfigFile = configRootDir.file("diktat.yml").asFile
    inputs {
        include("**/*.kt")
        include("**/*.gradle.kts")
        excludeNonLintedDirectories()
    }
    githubActions = false
    debug = false
}

tasks.withType<org.cqfn.diktat.plugin.gradle.DiktatJavaExecTaskBase>().configureEach {
    notCompatibleWithConfigurationCache("invocation of 'Task.project' at execution time is unsupported")
}

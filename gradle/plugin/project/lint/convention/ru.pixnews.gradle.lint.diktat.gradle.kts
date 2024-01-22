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
    id("com.saveourtool.diktat")
}

diktat {
    diktatConfigFile = configRootDir.file("diktat.yml").asFile
    inputs {
        include("**/*.kt")
        include("**/*.gradle.kts")
        excludeNonLintedDirectories()
    }
    reporters {
        plain()
        sarif()
    }
    debug = false
}

tasks.withType<com.saveourtool.diktat.plugin.gradle.tasks.DiktatTaskBase>().configureEach {
    notCompatibleWithConfigurationCache("invocation of 'Task.project' at execution time is unsupported")
}

tasks.named("mergeDiktatReports").configure {
    enabled = false
}

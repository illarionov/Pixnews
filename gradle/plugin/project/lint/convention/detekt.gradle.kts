/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.lint

import io.gitlab.arturbosch.detekt.Detekt
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that creates and configures task designated to run Detekt static code analyzer
 */
plugins {
    id("io.gitlab.arturbosch.detekt")
}

val detektCheck = tasks.register("detektCheck", Detekt::class) {
    description = "Custom detekt for to check all modules"

    config.setFrom(configRootDir.file("detekt.yml"))
    setSource(
        lintedFileTree
            .matching {
                exclude("**/resources/**")
            }
            .filter {
                it.name.endsWith(".kt") || it.name.endsWith(".kts")
            },
    )
    basePath = rootProject.projectDir.toString()

    parallel = true
    ignoreFailures = false
    buildUponDefaultConfig = true
    allRules = true

    reports {
        html.required.set(true)
        md.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)

        xml.outputLocation.set(file("build/reports/detekt/report.xml"))
        html.outputLocation.set(file("build/reports/detekt/report.html"))
        txt.outputLocation.set(file("build/reports/detekt/report.txt"))
        sarif.outputLocation.set(file("build/reports/detekt/report.sarif"))
    }
}

dependencies {
    detektPlugins(versionCatalog.findLibrary("detekt.formatting").get())
    detektPlugins(versionCatalog.findLibrary("detekt.compose.rules").get())
}

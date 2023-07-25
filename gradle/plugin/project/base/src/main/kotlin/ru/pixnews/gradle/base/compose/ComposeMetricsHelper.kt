/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.base.compose

import org.gradle.api.Project
import ru.pixnews.gradle.base.buildParameters

public fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters: MutableList<String> = mutableListOf()
    val enableMetrics = project.buildParameters.compose.enable_compose_compiler_metrics
    if (enableMetrics) {
        val metricsFolder = project.layout.buildDirectory.dir("compose-metrics").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${metricsFolder.absolutePath}",
        )
    }

    val enableReports = project.buildParameters.compose.enable_compose_compiler_reports
    if (enableReports) {
        val reportsFolder = project.layout.buildDirectory.dir("compose-reports").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${reportsFolder.absolutePath}",
        )
    }
    return metricParameters
}

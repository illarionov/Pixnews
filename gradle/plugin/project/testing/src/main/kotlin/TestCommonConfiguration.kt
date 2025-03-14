/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("TOO_MANY_LINES_IN_LAMBDA")

package ru.pixnews.gradle.project.testing

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.kotlin.dsl.dependencies
import ru.pixnews.gradle.project.base.UnitTestEngine
import ru.pixnews.gradle.project.base.UnitTestEngine.DISABLE
import ru.pixnews.gradle.project.base.UnitTestEngine.JUNIT4
import ru.pixnews.gradle.project.base.UnitTestEngine.JUNIT5
import ru.pixnews.gradle.project.base.UnitTestEngine.KOTEST
import ru.pixnews.gradle.project.base.buildParameters
import ru.pixnews.gradle.project.base.versionCatalog

public fun Project.configureCommonUnitTesting(
    engine: UnitTestEngine,
) {
    when (engine) {
        DISABLE -> kotlin.Unit
        JUNIT4 -> error("TODO")
        JUNIT5 -> configureCommonJunit5()
        KOTEST -> configureCommonKotest()
    }
}

public fun Test.configureCommonUnitTestingOptions(
    engine: UnitTestEngine,
) {
    if (this.name.endsWith("ScreenshotTest")) {
        return
    }
    when (engine) {
        JUNIT5, KOTEST -> configureCommonJunit5TestOptions()
        else -> Unit
    }
}

private fun Test.configureCommonJunit5TestOptions() {
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging {
        val logEvents = mutableSetOf(
            FAILED,
        )
        if (project.buildParameters.testing.verbose) {
            logEvents += setOf(
                PASSED,
                SKIPPED,
                STANDARD_ERROR,
            )
        }
        events = logEvents
    }
}

private fun Project.configureCommonJunit5() {
    configureCommonJunit5Dependencies()
    dependencies {
        add("testRuntimeOnly", versionCatalog.findLibrary("junit-jupiter-engine").orElseThrow())
    }
}

private fun Project.configureCommonKotest() {
    configureCommonJunit5Dependencies()
    dependencies {
        add("testImplementation", versionCatalog.findLibrary("kotest-runner-junit5").orElseThrow())
    }
}

private fun Project.configureCommonJunit5Dependencies() {
    dependencies {
        listOf(
            "kotest-bom",
            "junit-bom",
            "kotlinx-coroutines-bom",
        ).forEach {
            add("testImplementation", platform(versionCatalog.findLibrary(it).orElseThrow()))
        }
        listOf(
            "junit-jupiter",
            "kotest-assertions-core",
            "kotest-extensions-arrow",
            "kotlinx-coroutines-test",
        ).map { versionCatalog.findLibrary(it).orElseThrow() }
            .forEach {
                add("testImplementation", it)
            }
        add("testRuntimeOnly", versionCatalog.findLibrary("junit-platform-launcher").orElseThrow())
    }
}

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
@file:Suppress("TOO_MANY_LINES_IN_LAMBDA")

package ru.pixnews

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.kotlin.dsl.dependencies
import ru.pixnews.UnitTestEngine.DISABLE
import ru.pixnews.UnitTestEngine.JUNIT4
import ru.pixnews.UnitTestEngine.JUNIT5
import ru.pixnews.UnitTestEngine.KOTEST

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
        it.events = logEvents
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
            "junit5-bom",
        ).map { versionCatalog.findLibrary(it).orElseThrow() }
            .forEach {
                add("testImplementation", platform(it))
                add("testRuntimeOnly", platform(it))
            }

        listOf(
            "junit-jupiter-api",
            "kotest-assertions-core",
            "kotest-extensions-arrow",
            "kotlinx-coroutines-test",
        ).map { versionCatalog.findLibrary(it).orElseThrow() }
            .forEach {
                add("testImplementation", it)
            }
    }
}

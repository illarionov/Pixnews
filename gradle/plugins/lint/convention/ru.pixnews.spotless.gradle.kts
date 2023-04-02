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

import com.diffplug.gradle.spotless.SpotlessTask
import com.diffplug.spotless.FormatterStep
import com.diffplug.spotless.LineEnding
import com.diffplug.spotless.generic.LicenseHeaderStep
import ru.pixnews.configRootDir

/**
 * Convention plugin that configures Spotless
 */
plugins {
    id("com.diffplug.spotless")
}

spotless {
    isEnforceCheck = false

    // https://github.com/diffplug/spotless/issues/1527
    // https://github.com/diffplug/spotless/issues/1644
    lineEndings = LineEnding.PLATFORM_NATIVE

    val commonExcludes = listOf("**/build/**", "**/.gradle/**", "config/copyright/**")
    kotlin {
        target("**/*.kt")
        targetExclude(commonExcludes)

        diktat().configFile(configRootDir.file("diktat.yml"))
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude(commonExcludes)

        diktat().configFile(configRootDir.file("diktat.yml"))
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"), "(^(?![\\/ ]\\*).*$)")
    }
    java {
        target("**/*.java")
        targetExclude(commonExcludes)
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        importOrder("", "javax", "java", "\\#")

        indentWithSpaces()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovy {
        target("**/*.groovy")
        targetExclude(commonExcludes)
        importOrder("", "javax", "java", "\\#")
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovyGradle {
        target("*.gradle")
        targetExclude(commonExcludes)
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"), "(^(?![\\/ ]\\*).*$)")

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("yaml") {
        target("*.yml")
        targetExclude(commonExcludes)

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("toml") {
        target("*.toml")
        targetExclude(commonExcludes)

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("markdown") {
        target("**/*.md", "**/*.markdown")
        targetExclude(commonExcludes)

        endWithNewline()
    }
    format("protobuf") {
        target("**/*.proto")
        targetExclude(commonExcludes)

        // Disabled until https://github.com/diffplug/spotless/issues/673 is fixed
        // clangFormat("14.0.0-1ubuntu1").style("LLVM")
        endWithNewline()
    }
    format("xml") {
        target("**/*.xml")
        targetExclude(
            commonExcludes + listOf(
                ".idea/**/*.xml",
                "gradle/verification-metadata.xml",
                "**/res/xml/remote_config_defaults.xml",
            ),
        )

        val deactivatableLicenseStep: FormatterStep = run {
            val licenseHeaderFile = configRootDir.file("copyright/copyright.xml")
            val licenseHeaderContentProvider = providers.fileContents(licenseHeaderFile).asText
            val licenseHeaderFileStep = LicenseHeaderStep
                .headerDelimiter(licenseHeaderContentProvider::get, "(<[^!?])")
                .build()
            object : FormatterStep by licenseHeaderFileStep {
                override fun format(rawUnix: String, file: File): String? {
                    return if (!rawUnix.contains("<!-- spotless: off -->")) {
                        licenseHeaderFileStep.format(rawUnix, file)
                    } else {
                        rawUnix
                    }
                }
            }
        }
        addStep(deactivatableLicenseStep)

        endWithNewline()
    }
}

tasks.withType<SpotlessTask>().configureEach {
    notCompatibleWithConfigurationCache("https://github.com/diffplug/spotless/issues/987")
}

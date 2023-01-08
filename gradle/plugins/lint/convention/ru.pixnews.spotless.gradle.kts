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
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt", "config/copyright/**")

        diktat().configFile(configRootDir.file("diktat.yml"))
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**/*.gradle.kts", "config/copyright/**")

        diktat().configFile(configRootDir.file("diktat.yml"))
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"), "(^(?![\\/ ]\\*).*$)")
    }
    java {
        target("**/*.java")
        targetExclude("**/build/**/*.java", "config/copyright/**")
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        importOrder("", "javax", "java", "\\#")

        indentWithSpaces()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovy {
        target("**/*.groovy")
        targetExclude("**/build/**/*.groovy", "config/copyright/**")
        importOrder("", "javax", "java", "\\#")
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovyGradle {
        target("*.gradle")
        targetExclude("**/build/**/*.gradle", "config/copyright/**")
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"), "(^(?![\\/ ]\\*).*$)")

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("yaml") {
        target("*.yml")
        targetExclude("**/build/**/*.yml", "config/copyright/**")

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("toml") {
        target("*.toml")
        targetExclude("**/build/**/*.toml", "config/copyright/**")

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("markdown") {
        target("**/*.md", "**/*.markdown")
        targetExclude("**/build/**/*.md", "**/build/**/*.markdown", "config/copyright/**")

        endWithNewline()
    }
    format("protobuf") {
        target("**/*.proto")
        targetExclude("**/build/**/*.proto")

        clangFormat().style("LLVM")
        endWithNewline()
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml", ".idea/**/*.xml", "config/**", "gradle/verification-metadata.xml")

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

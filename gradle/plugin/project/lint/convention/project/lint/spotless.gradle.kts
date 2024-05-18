/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("SpacingBetweenPackageAndImports")

package ru.pixnews.gradle.project.lint

import com.diffplug.spotless.FormatterStep
import com.diffplug.spotless.generic.LicenseHeaderStep

/**
 * Convention plugin that configures Spotless
 */
plugins {
    id("com.diffplug.spotless")
}

spotless {
    isEnforceCheck = false

    val rootDir = lintedFileTree

    java {
        target(rootDir.filter { it.name.endsWith(".java") })
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        importOrder("", "javax", "java", "\\#")

        indentWithSpaces()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovy {
        target(rootDir.filter { it.name.endsWith(".groovy") })
        importOrder("", "javax", "java", "\\#")
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"))

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovyGradle {
        target(rootDir.filter { it.name.endsWith(".gradle") })
        licenseHeaderFile(configRootDir.file("copyright/copyright.kt"), "(^(?![\\/ ]\\*).*$)")

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("properties") {
        // "**/.gitignore" does not work: https://github.com/diffplug/spotless/issues/1146
        val propertyFiles = setOf(
            ".gitignore",
            ".gitattributes",
            ".editorconfig",
            "gradle.properties",
        )
        target(rootDir.filter { it.name in propertyFiles })

        trimTrailingWhitespace()
        endWithNewline()
    }
    format("yaml") {
        target(rootDir.filter { it.name.endsWith(".yml") })

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("toml") {
        target(rootDir.filter { it.name.endsWith(".toml") })

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("markdown") {
        target(rootDir.filter { it.name.endsWith(".md") || it.name.endsWith(".markdown") })

        endWithNewline()
    }
    format("protobuf") {
        target(rootDir.filter { it.name.endsWith(".proto") })

        // Disabled until https://github.com/diffplug/spotless/issues/673 is fixed
        // clangFormat("14.0.0-1ubuntu1").style("LLVM")
        endWithNewline()
    }
    format("xml") {
        target(
            rootDir
                .matching {
                    exclude(
                        "gradle/verification-metadata.xml",
                        "**/res/xml/remote_config_defaults.xml",
                    )
                }
                .filter { it.name.endsWith(".xml") },
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

tasks.withType<com.diffplug.gradle.spotless.SpotlessTask>().configureEach {
    notCompatibleWithConfigurationCache("https://github.com/diffplug/spotless/issues/987")
}

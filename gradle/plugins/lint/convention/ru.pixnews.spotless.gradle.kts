/**
 * Convention plugin that configures Spotless
 */

import ru.pixnews.configRootDir

plugins {
    id("com.diffplug.spotless")
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")

        diktat().configFile(configRootDir.file("diktat.yml"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**/*.gradle.kts")

        diktat().configFile(configRootDir.file("diktat.yml"))
    }
    java {
        target("**/*.java")
        targetExclude("**/build/**/*.java")

        importOrder("", "javax", "java", "\\#")

        indentWithSpaces()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovy {
        target("**/*.groovy")
        targetExclude("**/build/**/*.groovy")
        importOrder("", "javax", "java", "\\#")

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovyGradle {
        target("*.gradle")
        targetExclude("**/build/**/*.gradle")

        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("yaml") {
        target("*.yml")
        targetExclude("**/build/**/*.yml")

        trimTrailingWhitespace()
        endWithNewline()
        indentWithSpaces(2)
    }
    format("markdown") {
        target("**/*.md", "**/*.markdown")
        targetExclude("**/build/**/*.md", "**/build/**/*.markdown")

        endWithNewline()
    }
    format("protobuf") {
        target("**/*.proto")
        targetExclude("**/build/**/*.proto")

        clangFormat().style("LLVM")
        endWithNewline()
    }
    format("misc") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml", ".idea/**/*.xml")
        endWithNewline()
    }
}

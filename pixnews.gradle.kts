/*
 * SPDX-FileCopyrightText: 2025 Alexey Illarionov and the Pixnews project contributors
 * SPDX-License-Identifier: Apache-2.0
 */

plugins {
    id("ru.pixnews.gradle.project.lint.detekt")
    id("ru.pixnews.gradle.project.lint.spotless")
}

interface InjectedFileSystemOperations {
    @get:Inject val fs: FileSystemOperations
}
tasks.named("clean").configure {
    val fsOps: InjectedFileSystemOperations = project.objects.newInstance()
    val includedBuildsBuildDirs = gradle.includedBuilds.map { File(it.projectDir, "/build") }

    doLast {
        fsOps.fs.delete { delete(includedBuildsBuildDirs) }
    }
}

tasks.register("styleCheck") {
    group = "Verification"
    description = "Runs code style checking tools (excluding tests and Android Lint)"
    dependsOn("detektCheck", "spotlessCheck")
}

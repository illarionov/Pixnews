/*
 * Copyright (c) 2023-2025, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

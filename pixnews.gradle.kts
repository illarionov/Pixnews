/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.anvil) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.wire) apply false
    alias(libs.plugins.ben.manes.versions)
    id("ru.pixnews.gradle.lint.detekt")
    id("ru.pixnews.gradle.lint.spotless")
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

val styleCheck = tasks.register("styleCheck") {
    group = "Verification"
    description = "Runs code style checking tools (excluding tests and Android Lint)"
    dependsOn(tasks.named("detektCheck"), tasks.named("spotlessCheck"))
}

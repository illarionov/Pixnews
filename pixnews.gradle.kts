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
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.anvil) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.protobuf) apply false
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
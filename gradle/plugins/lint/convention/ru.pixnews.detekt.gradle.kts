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

import io.gitlab.arturbosch.detekt.Detekt
import ru.pixnews.configRootDir

/**
 * Convention plugin that creates and configures task designated to run Detekt static code analyzer
 */
plugins {
    id("io.gitlab.arturbosch.detekt")
}

val detektCheck = tasks.register("detektCheck", Detekt::class) {
    description = "Custom detekt for to check all modules"

    config.setFrom(configRootDir.file("detekt.yml"))
    setSource(rootProject.projectDir)
    basePath = rootProject.projectDir.toString()

    parallel = true
    ignoreFailures = false
    buildUponDefaultConfig = true
    allRules = true

    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**", "**/generated/**", "config/copyright/**")

    reports {
        html.required.set(true)
        md.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)

        xml.outputLocation.set(file("build/reports/detekt/report.xml"))
        html.outputLocation.set(file("build/reports/detekt/report.html"))
        txt.outputLocation.set(file("build/reports/detekt/report.txt"))
        sarif.outputLocation.set(file("build/reports/detekt/report.sarif"))
    }
}

// https://github.com/gradle/gradle/issues/22468
if (project.name != "gradle-kotlin-dsl-accessors") {
    val versionCatalog = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        detektPlugins(versionCatalog.findLibrary("detekt.formatting").get())
    }
}
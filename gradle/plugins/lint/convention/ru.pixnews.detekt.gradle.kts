/**
 * Convention plugin that creates and configures task designated to run Detekt static code analyzer
 */

import io.gitlab.arturbosch.detekt.Detekt
import ru.pixnews.configRootDir

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
    exclude("**/resources/**", "**/build/**", "**/generated/**")

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

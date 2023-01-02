plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ben.manes.versions)
    id("ru.pixnews.detekt")
}

tasks.register<Delete>("clean") {
    group = "build"
    delete(rootProject.buildDir)
    gradle.includedBuilds.forEach {
        delete(File(it.projectDir, "/build"))
    }
}

tasks.register("styleCheck") {
    group = "Verification"
    description = "Runs code style checking tools (excluding tests and Android Lint)"
    dependsOn(tasks.named("detektCheck"))
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ben.manes.versions)
}

tasks.register<Delete>("clean") {
    group = "build"
    delete(rootProject.buildDir)
    gradle.includedBuilds.forEach {
        delete(File(it.projectDir, "/build"))
    }
}
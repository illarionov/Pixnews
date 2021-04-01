
tasks.register<Delete>("clean") {
    group = "build"
    delete(rootProject.buildDir)
    gradle.includedBuilds.forEach {
        delete(File(it.projectDir, "/build"))
    }
}
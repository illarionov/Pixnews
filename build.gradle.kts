plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ben.manes.versions)
    id("ru.pixnews.detekt")
    id("ru.pixnews.spotless")
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

tasks.matching { it.name == LifecycleBasePlugin.CHECK_TASK_NAME }.configureEach {
    dependsOn(styleCheck)
}

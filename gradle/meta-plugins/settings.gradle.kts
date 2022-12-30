dependencyResolutionManagement {
    repositories.gradlePluginPortal()
}

pluginManagement {
    includeBuild("../base-kotlin-dsl-plugin")
}

include("build-parameters")
include("settings")

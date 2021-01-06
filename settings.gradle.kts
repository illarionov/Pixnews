rootProject.name = "PixRadar"

pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        jcenter()
    }
}

includeBuild("build-logic/versions/libraries")
includeBuild("build-logic")

include(":app")

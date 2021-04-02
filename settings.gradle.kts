rootProject.name = "PixRadar"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                // https://youtrack.jetbrains.com/issue/IDEA-261387
                includeModule("org.jetbrains.trove4j", "trove4j")
            }
        }
    }
}

includeBuild("build-logic")

include(":app")

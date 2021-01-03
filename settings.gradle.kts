rootProject.name = "PixRadar"

includeBuild("includedBuild/dependencies")

dependencyResolutionManagement {
    repositories {
        google()
        jcenter()
    }
}


include(":app")

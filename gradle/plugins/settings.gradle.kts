
pluginManagement {
    includeBuild("../meta-plugins")
}

plugins {
    id("ru.pixnews.settings")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("android")


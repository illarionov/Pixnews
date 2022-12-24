
pluginManagement {
    includeBuild("../meta-plugins")
}

plugins {
    id("ru.x0xdc.pixradar.settings")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("android")



pluginManagement {
    includeBuild("../base-kotlin-dsl-plugin")
    includeBuild("../meta-plugins")
}

plugins {
    id("ru.pixnews.settings")
}

dependencyResolutionManagement {
    includeBuild("../meta-plugins") // for 'build-parameters'
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("android")
include("kotlin")


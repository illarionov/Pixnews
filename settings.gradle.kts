pluginManagement {
    includeBuild("gradle/meta-plugins")
}

plugins {
    id("ru.x0xdc.pixradar.settings")
}

rootProject.name = "PixRadar"

include(":app")

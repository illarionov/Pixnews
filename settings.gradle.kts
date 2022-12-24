pluginManagement {
    includeBuild("gradle/meta-plugins")
}

plugins {
    id("ru.pixnews.settings")
}

rootProject.name = "PixRadar"

include(":app")

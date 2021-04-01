plugins {
    `java-platform`
    id("ru.x0xdc.pixradar.libraries")
}

javaPlatform.allowDependencies()

group = "ru.x0xdc.pixradar.buildlogic.versions"

dependencies {
    constraints {
        //api("${libs.android.tools_build_gradle}:7.0.0-alpha12")
        api("${libs.android.tools_build_gradle}:4.1.3")
        api("${libs.kotlin.android_gradle_plugin}:1.4.32")
        api("${libs.kotlin.jvm_gradle_plugin}:1.4.32")
    }
}

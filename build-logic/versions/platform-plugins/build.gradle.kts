plugins {
    `java-platform`
}

javaPlatform.allowDependencies()

group = "ru.x0xdc.pixradar.buildlogic.versions"

dependencies {
    constraints {
        api("com.android.tools.build:gradle:7.0.0-alpha15")
        api("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.4.32")
        api("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.4.32")
    }
}

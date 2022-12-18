plugins {
    `java-platform`
}

javaPlatform.allowDependencies()

group = "ru.x0xdc.pixradar.buildlogic.versions"

dependencies {
    constraints {
        api("com.android.tools.build:gradle:8.0.0-alpha09")
        api("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.8.0-RC")
        api("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.8.0-RC")
    }
}

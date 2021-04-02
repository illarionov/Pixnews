plugins {
    `kotlin-dsl`
}

group = "ru.x0xdc.pixradar.buildlogic"

dependencies {
    implementation(platform("ru.x0xdc.pixradar.buildlogic.versions:platform-plugins"))

    implementation("com.android.tools.build:gradle")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin")
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
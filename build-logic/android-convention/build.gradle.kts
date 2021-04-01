plugins {
    `kotlin-dsl`
    id("ru.x0xdc.pixradar.libraries")
}

group = "ru.x0xdc.pixradar.buildlogic"

dependencies {
    implementation(platform("ru.x0xdc.pixradar.buildlogic.versions:platform-plugins"))
    implementation("ru.x0xdc.pixradar.buildlogic.versions:libraries")
    implementation(libs.android.tools_build_gradle)
    implementation(libs.kotlin.android_gradle_plugin)
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
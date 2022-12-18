plugins {
    `kotlin-dsl`
}

group = "ru.x0xdc.pixradar.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.agp.plugin)
    implementation(libs.kotlin.jvm.plugin)
}
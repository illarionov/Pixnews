plugins {
    id("ru.pixnews.kotlindsl")
}

dependencies {
    implementation(libs.agp.plugin)
    implementation(libs.kotlin.jvm.plugin)
    implementation("ru.pixnews:build-parameters")
}
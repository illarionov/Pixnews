// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra["kotlin_version"] = "1.4.10"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha13")
        classpath(kotlin("gradle-plugin", version = project.extra["kotlin_version"].toString()))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    group = "build"
    delete(rootProject.buildDir)
}

tasks.wrapper {
    //noinspection UnnecessaryQualifiedReference
    distributionType = Wrapper.DistributionType.ALL
}
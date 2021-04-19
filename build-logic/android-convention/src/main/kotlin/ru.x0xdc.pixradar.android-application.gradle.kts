@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 23
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        useIR = true
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(platform("ru.x0xdc.pixradar.buildlogic.versions:platform"))
        testImplementation(platform("ru.x0xdc.pixradar.buildlogic.versions:platform"))
        androidTestImplementation(platform("ru.x0xdc.pixradar.buildlogic.versions:platform"))
    }
}
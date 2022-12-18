@file:Suppress("UnstableApiUsage")

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30
    namespace = "ru.x0xdc.pixradar"

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
    }

    dependencies {
        implementation(kotlin("stdlib"))
        versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
            implementation(platform(it))
            testImplementation(platform(it))
            androidTestImplementation(platform(it))
        }

    }
}
import ru.x0xdc.pixradar.dependencies.Deps

plugins {
    id("com.android.application")
    id("dependencies")
    kotlin("android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "ru.x0xdc.pixradar"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//    implementation(Deps.kotlin.stdlib)
    implementation(Deps.android.androidx.core)
    implementation(Deps.android.androidx.appcompat)
    implementation(Deps.android.material)
    implementation(Deps.android.androidx.constraintLayout)
    implementation(Deps.android.androidx.navigation.fragment)
    implementation(Deps.android.androidx.navigation.ui)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.android.androidx.test.ext_junit)
    androidTestImplementation(Deps.android.androidx.test.espresso_core)
}
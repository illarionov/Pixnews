plugins {
    id("ru.x0xdc.pixradar.android-application")
}

android {
    defaultConfig {
        applicationId = "ru.x0xdc.pixradar"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.android.androidx.core)
    implementation(libs.android.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.androidx.constraintLayout)
    implementation(libs.android.androidx.navigation.fragment)
    implementation(libs.android.androidx.navigation.ui)

    testImplementation(libs.junit)

    androidTestImplementation(libs.android.androidx.test.ext_junit)
    androidTestImplementation(libs.android.androidx.test.espresso_core)
}
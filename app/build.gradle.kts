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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("androidx.constraintlayout:constraintlayout")
    implementation("androidx.navigation:navigation-fragment-ktx")
    implementation("androidx.navigation:navigation-ui-ktx")

    testImplementation("junit:junit")

    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core")
}
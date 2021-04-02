plugins {
    `java-platform`
}

javaPlatform.allowDependencies()

group = "ru.x0xdc.pixradar.buildlogic.versions"

val navigationVersion = "2.3.2"
val appCompatVersion = "1.2.0"
val constraintLayoutVersion = "2.1.0-alpha2"
val coreTestingVersion = "2.1.0"
val lifecycleVersion = "2.3.0-rc01"
val roomVersion = "2.2.6"
val espressoVersion = "3.3.0"
val androidxJunitVersion = "1.1.2"

dependencies {
    api(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.4.2"))

    constraints {
        api("junit:junit:4.13.1")

        api("com.google.android.material:material:1.3.0-beta01")
        api("androidx.activity:activity-ktx:1.2.0-rc01")
        api("androidx.appcompat:appcompat:$appCompatVersion")
        api("androidx.core:core-ktx:1.3.2")
        api("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

        api("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
        api("androidx.navigation:navigation-ui-ktx:$navigationVersion")

        api("androidx.test.ext:junit:$androidxJunitVersion")
        api("androidx.test.espresso:espresso-core:$espressoVersion")
        api("androidx.arch.core:core-testing:$coreTestingVersion")

        api("androidx.room:room-ktx:$roomVersion")
        api("androidx.room:room-compiler:$roomVersion")
        api("androidx.room:room-testing:$roomVersion")

        api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
        api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
        api("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    }
}
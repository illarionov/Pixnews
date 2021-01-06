plugins {
    `java-platform`
    id("ru.x0xdc.pixradar.libraries")
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
    api(platform("${libs.kotlin.coroutines.bom}:1.4.2"))

    constraints {
        api("${libs.junit}:4.13.1")

        api("${libs.android.material}:1.3.0-beta01")
        api("${libs.android.androidx.activity}:1.2.0-rc01")
        api("${libs.android.androidx.appcompat}:$appCompatVersion")
        api("${libs.android.androidx.core}:1.3.2")
        api("${libs.android.androidx.constraintLayout}:$constraintLayoutVersion")

        api("${libs.android.androidx.navigation.fragment}:$navigationVersion")
        api("${libs.android.androidx.navigation.ui}:$navigationVersion")

        api("${libs.android.androidx.test.ext_junit}:$androidxJunitVersion")
        api("${libs.android.androidx.test.espresso_core}:$espressoVersion")
        api("${libs.android.androidx.test.arch_core_testing}:$coreTestingVersion")

        api("${libs.android.androidx.room}:$roomVersion")
        api("${libs.android.androidx.room.compiler}:$roomVersion")
        api("${libs.android.androidx.room.testing}:$roomVersion")

        api("${libs.android.androidx.lifecycle.viewmodel}:$lifecycleVersion")
        api("${libs.android.androidx.lifecycle.livedata}:$lifecycleVersion")
        api("${libs.android.androidx.lifecycle.common_java8}:$lifecycleVersion")
    }
}
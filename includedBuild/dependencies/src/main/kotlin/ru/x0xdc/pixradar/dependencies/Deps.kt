package ru.x0xdc.pixradar.dependencies

object Deps {
    private const val coroutinesVersion = "1.4.2"

    val kotlin = Kotlin
    val android = Android
    const val junit = "junit:junit:4.13.1"

    object Kotlin {

        val coroutines = Coroutines

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        }
    }

    object Android {
        private const val navigationVersion = "2.3.2"
        private const val appCompatVersion = "1.2.0"
        private const val constraintLayoutVersion = "2.1.0-alpha2"
        private const val coreTestingVersion = "2.1.0"
        private const val lifecycleVersion = "2.3.0-rc01"
        private const val roomVersion = "2.2.6"
        private const val espressoVersion = "3.3.0"
        private const val androidxJunitVersion = "1.1.2"

        val androidx = Androidx
        const val material = "com.google.android.material:material:1.3.0-beta01"

        object Androidx {
            const val activity = "androidx.activity:activity-ktx:1.2.0-rc01"
            const val appcompat = "androidx.appcompat:appcompat:$appCompatVersion"
            const val core = "androidx.core:core-ktx:1.3.2"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

            val navigation = Navigation
            val room = Room
            val test = Testing
            val lifecycle = Lifecycle

            object Navigation {
                const val fragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
                const val ui = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
            }

            object Testing {
                const val ext_junit = "androidx.test.ext:junit:$androidxJunitVersion"
                const val espresso_core = "androidx.test.espresso:espresso-core:$espressoVersion"
                const val arch_core_testing = "androidx.arch.core:core-testing:$coreTestingVersion"
            }

            object Room {
                const val room = "androidx.room:room-ktx:$roomVersion"
                const val compiler = "androidx.room:room-compiler:$roomVersion"
                const val testing = "androidx.room:room-testing:$roomVersion"
            }

            object Lifecycle {
                const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
                const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
                const val common_java8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
            }
        }
    }
}
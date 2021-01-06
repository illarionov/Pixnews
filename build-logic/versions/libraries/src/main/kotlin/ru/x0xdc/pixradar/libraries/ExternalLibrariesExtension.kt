package ru.x0xdc.pixradar.libraries

abstract class ExternalLibrariesExtension {

    val kotlin = Kotlin
    val android = Android
    val junit = "junit:junit"

    object Kotlin {

        val coroutines = Coroutines
        val android_gradle_plugin = "org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin"
        val jvm_gradle_plugin = "org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin"


        object Coroutines {
            val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core"
            val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android"
            val bom = "org.jetbrains.kotlinx:kotlinx-coroutines-bom"
        }
    }

    object Android {
        val androidx = Androidx
        const val material = "com.google.android.material:material"
        const val tools_build_gradle = "com.android.tools.build:gradle"

        object Androidx {
            const val activity = "androidx.activity:activity-ktx"
            const val appcompat = "androidx.appcompat:appcompat"
            const val core = "androidx.core:core-ktx"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout"

            val navigation = Navigation
            val room = Room
            val test = Testing
            val lifecycle = Lifecycle

            object Navigation {
                const val fragment = "androidx.navigation:navigation-fragment-ktx"
                const val ui = "androidx.navigation:navigation-ui-ktx"
            }

            object Testing {
                const val ext_junit = "androidx.test.ext:junit"
                const val espresso_core = "androidx.test.espresso:espresso-core"
                const val arch_core_testing = "androidx.arch.core:core-testing"
            }

            object Room {
                const val room = "androidx.room:room-ktx"
                const val compiler = "androidx.room:room-compiler"
                const val testing = "androidx.room:room-testing"
            }

            object Lifecycle {
                const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx"
                const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx"
                const val common_java8 = "androidx.lifecycle:lifecycle-common-java8"
            }
        }
    }
}
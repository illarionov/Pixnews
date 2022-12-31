@file:Suppress("UnstableApiUsage")

package ru.pixnews

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maybeCreate

internal fun Project.configureTestManagedDevices(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        testOptions {
            managedDevices {
                val pixel5api33 = devices.maybeCreate<ManagedVirtualDevice>("pixel5api33").apply {
                    device = "Pixel 5"
                    apiLevel = 33
                    systemImageSource = "google"
                }
                val pixel2api30 = devices.maybeCreate<ManagedVirtualDevice>("pixel2api30").apply {
                    device = "Pixel 2"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }
                groups {
                    maybeCreate("phone").apply {
                        targetDevices += listOf(pixel5api33, pixel2api30)
                    }
                }
            }
        }
    }
}

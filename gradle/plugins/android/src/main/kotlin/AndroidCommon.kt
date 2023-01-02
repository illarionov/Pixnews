@file:Suppress("MagicNumber")

package ru.pixnews

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

internal fun Project.configureCommonAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        compileSdk = 33
        namespace = "ru.pixnews"

        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            resourceConfigurations.addAll(listOf("en", "ru"))
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                    // There are some plugins incompatible with K2 compiler:
                    // androidx.compose.compiler.plugins.kotlin.ComposeComponentRegistrar
                    // useK2.set(true)
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-opt-in=kotlinx.coroutines.FlowPreview",
                        "-opt-in=kotlin.Experimental",
                    )
                }
            }

        testOptions {
            animationsDisabled = true
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        lint {
            quiet = false
            ignoreWarnings = false
            htmlReport = true
            xmlReport = true
            checkDependencies = true
            ignoreTestSources = true
        }
    }

    dependencies {
        add("implementation", kotlin("stdlib"))
        add(
            "coreLibraryDesugaring",
            versionCatalog.findLibrary("android.desugar.jdk.libs").orElseThrow(),
        )
    }
}

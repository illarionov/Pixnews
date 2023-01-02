/**
 * Convention plugin for use kotlin only modules
 */

import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

plugins {
    kotlin("jvm")
    id("com.android.lint")
}

kotlin {
    explicitApi = ExplicitApiMode.Warning
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
    .configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            useK2.set(true)
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )
        }
    }

internal val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", kotlin("stdlib"))
    versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
        implementation(platform(it))
        testImplementation(platform(it))
    }
    testImplementation(kotlin("test"))
}

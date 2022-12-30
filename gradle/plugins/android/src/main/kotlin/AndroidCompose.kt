package ru.pixnews

import com.android.build.api.dsl.CommonExtension
import gradle.kotlin.dsl.accessors._c82ff6998eca14d52430c6ee9eb79469.buildParameters
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versionCatalog.findVersion("androidx-compose-compiler").get().toString()
        }

        dependencies {
            val bom = versionCatalog.findLibrary("androidx-compose-bom").orElseThrow()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
        .configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.0"
                ))
            }
        }

    configureComposeMetrics()
}

private fun Project.configureComposeMetrics() {
    val metricsParams = buildComposeMetricsParameters()
    if (metricsParams.isNotEmpty()) {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
            .configureEach {
                compilerOptions {
                    freeCompilerArgs.addAll(metricsParams)
                }
            }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetrics = project.buildParameters.compose.enable_compose_compiler_metrics
    if (enableMetrics) {
        val metricsFolder = project.layout.buildDirectory.dir("compose-metrics").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${metricsFolder.absolutePath}"
        )
    }

    val enableReports = project.buildParameters.compose.enable_compose_compiler_reports
    if (enableReports) {
        val reportsFolder = project.layout.buildDirectory.dir("compose-reports").get().asFile
        metricParameters += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${reportsFolder.absolutePath}"
        )
    }
    return metricParameters
}


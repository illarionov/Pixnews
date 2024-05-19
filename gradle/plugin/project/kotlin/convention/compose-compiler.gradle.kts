/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.kotlin

import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.pixnews.gradle.project.base.compose.buildComposeMetricsParameters
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that enables Compose Compiler on kotlin-only modules.
 *
 * Used to infer stability of the data-classes.
 */
plugins {
    id("ru.pixnews.gradle.base.build-parameters")
}

private val composeCompilerPluginClasspath = createComposeCompilerPluginClasspath()
private val composeRuntimeCompileClasspath = createComposeRuntimeCompileClasspath()

private val metricsPrams = buildComposeMetricsParameters()
tasks.withType<KotlinCompile>()
    .matching { it !is KaptGenerateStubsTask }
    .configureEach {
        pluginClasspath.from(composeCompilerPluginClasspath)
        libraries.from(composeRuntimeCompileClasspath)
        compilerOptions {
            freeCompilerArgs.add("-Xallow-unstable-dependencies")
            freeCompilerArgs.addAll(metricsPrams)
            freeCompilerArgs.addAll(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.9.24",
            )
        }
    }

fun createComposeCompilerPluginClasspath(): FileCollection {
    val composeCompilerConfiguration: Configuration by configurations.creating {
        isTransitive = false
        isCanBeConsumed = false
        isCanBeResolved = true
        isVisible = false
        defaultDependencies {
            add(
                dependencyFactory.create(
                    "androidx.compose.compiler:compiler:" +
                            versionCatalog.findVersion("androidx-compose-compiler").get().toString(),
                ),
            )
        }
    }
    return project.files(composeCompilerConfiguration)
}

fun createComposeRuntimeCompileClasspath(): FileCollection {
    val compileClasspathAarConfiguration = configurations.create("compileClasspathAar") {
        isCanBeConsumed = false
        isCanBeResolved = true
        isVisible = false
        attributes {
            attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "aar")
            attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_API))
            attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        }
    }

    dependencies {
        add(
            compileClasspathAarConfiguration.name,
            platform(versionCatalog.findLibrary("chrisbanes-compose-bom").get()),
        )
        add(compileClasspathAarConfiguration.name, versionCatalog.findLibrary("androidx-compose-runtime").get())

        registerTransform(UnpackAarClassesTransform::class.java) {
            from.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "aar")
            to.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar")
        }
    }

    return compileClasspathAarConfiguration
        .incoming
        .artifactView {
            attributes {
                attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar")
            }
        }
        .files
}

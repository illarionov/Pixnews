/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.pixnews.gradle.base.compose.buildComposeMetricsParameters
import ru.pixnews.gradle.base.versionCatalog
import ru.pixnews.gradle.kotlin.UnpackAarClassesTransform

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
                "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.21",
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
        add(compileClasspathAarConfiguration.name, platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
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

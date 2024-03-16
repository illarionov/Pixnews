import java.nio.file.Paths
import kotlin.io.path.exists

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.di.anvil-factories")
}

pixnews {
    compose.set(false)
}

configurations {
    dependencyScope("graalvmCompiler")
    resolvable("graalvmCompilerClasspath") {
        extendsFrom(configurations["graalvmCompiler"])
    }
}

dependencies {
    add("graalvmCompiler", "org.graalvm.compiler:compiler:24.0.1")
}

val isRunningOnGraalVm: Provider<Boolean> = providers.gradleProperty("GRAALVM")
    .map(String::toBoolean)
    .orElse(
        providers
            .systemProperty("java.home")
            .map { javaHome ->
                Paths.get("$javaHome/lib/graalvm").exists()
            },
    )
    .orElse(false)

android {
    namespace = "ru.pixnews.feature.calendar.data"
    testOptions {
        unitTests.all { testTask ->
            testTask.javaLauncher = javaToolchains.launcherFor {
                languageVersion = JavaLanguageVersion.of(22)
            }
            testTask.jvmArgumentProviders += GraalvmCompilerJvmArgumentsProvider(
                configurations["graalvmCompilerClasspath"],
                isRunningOnGraalVm,
            )
        }
    }
}

class GraalvmCompilerJvmArgumentsProvider(
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val gralvmClasspath: FileCollection,
    val isGralvm: Provider<Boolean>,
) : CommandLineArgumentProvider {
    override fun asArguments(): Iterable<String> {
        return if (!isGralvm.get()) {
            listOf(
                "-XX:+UnlockExperimentalVMOptions",
                "-XX:+EnableJVMCI",
                "--upgrade-module-path=${gralvmClasspath.asPath}",
            )
        } else {
            emptyList()
        }
    }
}

dependencies {
    api(projects.feature.calendar.dataPublic)
    implementation(projects.foundation.appconfig)
    implementation(projects.foundation.database)
    implementation(projects.foundation.coroutines)
    implementation(projects.foundation.di.base)
    implementation(projects.foundation.domainModel)
    implementation(projects.foundation.network.public)
    implementation(projects.library.functional)
    implementation(projects.library.kotlinUtils)
    implementation(projects.library.coroutines)

    api(libs.inject)
    implementation(libs.kermit)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(testFixtures(projects.foundation.domainModel))
    testImplementation(projects.library.test)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.pixnews.sqlite.open.helper.graal)
    testImplementation(libs.pixnews.sqlite.open.helper.main)
    testImplementation(libs.pixnews.sqlite.open.helper.wasm)
    testImplementation(libs.graalvm.polyglot.polyglot)
    testImplementation(libs.graalvm.polyglot.wasm)
}

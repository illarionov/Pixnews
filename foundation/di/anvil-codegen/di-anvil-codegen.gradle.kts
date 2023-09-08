/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

plugins {
    id("ru.pixnews.gradle.kotlin.library")
    kotlin("kapt")
}

group = "ru.pixnews.foundation.di.anvil-codegen"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=com.squareup.anvil.annotations.ExperimentalAnvilApi",
            "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
        )
    }
}

dependencies {
    implementation(projects.foundation.di.base)

    api(libs.inject)
    api(libs.anvil.compiler.api)
    implementation(libs.anvil.compiler.utils)
    implementation(libs.kotlinpoet)
    implementation(libs.dagger)

    compileOnly(libs.auto.service.annotations)
    kapt(libs.auto.service.compiler)

    testImplementation(libs.anvil.annotations.optional)
    testImplementation(testFixtures(libs.anvil.compiler.utils))
    testImplementation(libs.kotest.assertions.core)
}

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

    testImplementation(libs.kotest.assertions.core)
    testImplementation(testFixtures(libs.anvil.compiler.utils))
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.di

import com.google.devtools.ksp.gradle.KspTaskJvm
import org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.pixnews.gradle.project.base.versionCatalog

/**
 * Convention plugin that configures anvil with kapt
 */
plugins {
    kotlin("kapt")
    id("com.squareup.anvil")
}

dependencies {
    listOf(
        "pixnews-anvil-activity-generator",
        "pixnews-anvil-experiment-generator",
        "pixnews-anvil-initializer-generator",
        "pixnews-anvil-test-generator",
        "pixnews-anvil-viewmodel-generator",
        "pixnews-anvil-workmanager-generator",
    ).forEach {
        add("anvil", versionCatalog.findLibrary(it).get())
    }
    add("api", versionCatalog.findLibrary("dagger").orElseThrow())
    add("kapt", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
}

anvil {
    addOptionalAnnotations.set(true)
}

kapt {
    javacOptions {
        option("-Adagger.fastInit=enabled")
        option("-Adagger.strictMultibindingValidation=enabled")
        option("-Adagger.experimentalDaggerErrorMessages=enabled")
    }
}

// https://github.com/square/anvil/issues/693#issuecomment-1744013947
tasks.withType<KotlinCompile>().configureEach {
    if (this !is KspTaskJvm && this !is KaptGenerateStubs) {
        val anvilSrcGenDir = layout.buildDirectory.dir(sourceSetName.map { "anvil/src-gen-$it" })
        this.outputs.dir(anvilSrcGenDir)
    }
}

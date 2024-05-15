/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.TestPlugin
import ru.pixnews.gradle.base.versionCatalog
import ru.pixnews.gradle.testing.graalvm.GraalvmCompilerJvmArgumentsProvider
import ru.pixnews.gradle.testing.graalvm.isRunningOnGraalVm

/**
 * Convention plugin that configures Graalvm compiler for use in unit tests
 */
configurations {
    dependencyScope("graalvmCompiler")
    resolvable("graalvmCompilerClasspath") {
        extendsFrom(configurations["graalvmCompiler"])
    }
}

dependencies {
    add("graalvmCompiler", project.versionCatalog.findLibrary("graalvm.compiler").get())
}

listOf(
    AppPlugin::class.java,
    LibraryPlugin::class.java,
    DynamicFeaturePlugin::class.java,
    TestPlugin::class.java,
).forEach { agpPluginType ->
    plugins.withType(agpPluginType) {
        extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
            testOptions {
                targetSdk = 26
                unitTests.all { testTask ->
                    testTask.configureGraalvmToolchainArgs(the<JavaToolchainService>())
                }
            }
        }
    }
}

fun Test.configureGraalvmToolchainArgs(javaToolchainService: JavaToolchainService) {
    javaLauncher = javaToolchainService.launcherFor {
        languageVersion = JavaLanguageVersion.of(22)
    }
    jvmArgumentProviders += GraalvmCompilerJvmArgumentsProvider(
        configurations["graalvmCompilerClasspath"],
        isRunningOnGraalVm,
    )
}

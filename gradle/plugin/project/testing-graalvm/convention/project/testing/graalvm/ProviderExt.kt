/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.testing.graalvm

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import java.nio.file.Paths
import kotlin.io.path.exists

internal val Project.isRunningOnGraalVm: Provider<Boolean>
    get() = providers.gradleProperty("GRAALVM")
        .map(String::toBoolean)
        .orElse(
            providers
                .systemProperty("java.home")
                .map { javaHome ->
                    Paths.get("$javaHome/lib/graalvm").exists()
                },
        )
        .orElse(false)

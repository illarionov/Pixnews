/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.testing.graalvm

import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.process.CommandLineArgumentProvider

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

/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.kotlin

import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.work.DisableCachingByDefault
import java.nio.file.Files
import java.util.zip.ZipFile

@DisableCachingByDefault(because = "Not worth caching")
abstract class UnpackAarClassesTransform : TransformAction<TransformParameters.None> {
    @get:InputArtifact
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val inputAarFile: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs) {
        ZipFile(inputAarFile.get().asFile).use { inputAar ->
            val outputFileName = "${inputAarFile.get().asFile.nameWithoutExtension}-classes.jar"
            val outputJar = outputs.file(outputFileName).toPath()

            inputAar.getEntry("classes.jar")?.also { entry ->
                inputAar.getInputStream(entry).use {
                    Files.copy(it, outputJar)
                }
            }
        }
    }
}

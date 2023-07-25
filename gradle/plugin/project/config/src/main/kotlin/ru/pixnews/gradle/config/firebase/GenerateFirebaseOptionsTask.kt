/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.config.firebase

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Generates GeneratedFirebaseOptions.firebaseOptions using values from
 * configuration file
 */
abstract class GenerateFirebaseOptionsTask : DefaultTask() {
    @get:Input
    @get:Optional
    abstract val firebaseConfig: Property<LocalFirebaseOptions>

    @get:OutputDirectory
    abstract val sourceOutputDir: DirectoryProperty

    @TaskAction
    fun doTaskAction() {
        val codegenDir = sourceOutputDir.asFile.get()
        codegenDir.listFiles()?.forEach { it.deleteRecursively() }

        val config = if (firebaseConfig.isPresent) {
            firebaseConfig.get()
        } else {
            logger.warn(
                """
                Configuration file is not installed. Will use the default firebase values.
                Please copy config/pixnews.properties.sample to config/pixnews.properties and set the firebase
                configuration parameters.
            """.trimIndent(),
            )
            LocalFirebaseOptions.empty
        }

        FirebaseOptionsGenerator(
            options = config,
            codeGenDir = codegenDir,
        ).generate()
    }
}

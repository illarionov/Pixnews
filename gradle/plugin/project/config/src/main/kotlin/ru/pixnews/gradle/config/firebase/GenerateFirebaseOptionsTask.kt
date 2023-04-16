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

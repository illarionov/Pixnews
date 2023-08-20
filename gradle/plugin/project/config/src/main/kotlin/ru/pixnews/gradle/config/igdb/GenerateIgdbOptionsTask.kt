/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.config.igdb

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import ru.pixnews.gradle.config.util.getWarnIfNotPresent

/**
 * Generates IgdbConfig using values from configuration file
 */
abstract class GenerateIgdbOptionsTask : DefaultTask() {
    @get:Input
    @get:Optional
    abstract val igdbConfig: Property<LocalIgdbOptions>

    @get:OutputDirectory
    abstract val sourceOutputDir: DirectoryProperty

    @TaskAction
    fun doTaskAction() {
        val codegenDir = sourceOutputDir.asFile.get()
        codegenDir.listFiles()?.forEach { it.deleteRecursively() }

        val config = igdbConfig.getWarnIfNotPresent(
            logger = logger,
            name = "IGDB",
            ifNotPresent = LocalIgdbOptions::empty,
        )
        IgdbOptionsGenerator(
            options = config,
            codeGenDir = codegenDir,
        ).generate()
    }
}

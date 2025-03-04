/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project

import buildparameters.BuildParametersExtension
import com.android.build.api.variant.AndroidComponentsExtension
import ru.pixnews.gradle.project.config.igdb.GenerateIgdbOptionsTask
import ru.pixnews.gradle.project.config.igdb.LocalIgdbOptionsValueSource
import ru.pixnews.gradle.project.config.util.withAnyOfAndroidPlugins

/**
 * Convention plugin that generates GeneratedIgdbConfig using values from
 * configuration file
 */
project.withAnyOfAndroidPlugins { _, androidComponentsExtension ->
    with(androidComponentsExtension) {
        registerIgdbOptionsTask()
    }
}

fun AndroidComponentsExtension<*, *, *>.registerIgdbOptionsTask() {
    val igdbOptionsProvider = providers.of(LocalIgdbOptionsValueSource::class) {
        parameters {
            configFilePath.set(
                layout.settingsDirectory.file(
                    providers.provider { extensions.getByType<BuildParametersExtension>().config },
                ),
            )
        }
    }

    onVariants { variant ->
        @Suppress("GENERIC_VARIABLE_WRONG_DECLARATION")
        val igdbOptionsTaskProvider = project.tasks.register<GenerateIgdbOptionsTask>(
            "${variant.name}GenerateIgdbOptions",
        ) {
            group = "Build"
            igdbConfig.set(igdbOptionsProvider)
            sourceOutputDir.set(layout.buildDirectory.dir("igdb-options"))
        }

        variant.sources.java?.addGeneratedSourceDirectory(
            taskProvider = igdbOptionsTaskProvider,
            wiredWith = GenerateIgdbOptionsTask::sourceOutputDir,
        )
    }
}

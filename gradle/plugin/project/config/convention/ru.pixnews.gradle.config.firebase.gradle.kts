/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import buildparameters.BuildParametersExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.ResValue
import ru.pixnews.gradle.config.firebase.GenerateFirebaseOptionsTask
import ru.pixnews.gradle.config.firebase.LocalFirebaseOptions
import ru.pixnews.gradle.config.firebase.LocalFirebaseOptionsValueSource
import ru.pixnews.gradle.config.util.withAnyOfAndroidPlugins

/**
 * Convention plugin that generates GeneratedFirebaseOptions.firebaseOptions using values from
 * configuration file and prepares required resource values.
 */
project.withAnyOfAndroidPlugins { _, androidComponentsExtension ->
    with(androidComponentsExtension) {
        registerFirebaseOptionsTask()
    }
}

fun AndroidComponentsExtension<*, *, *>.registerFirebaseOptionsTask() {
    val pixnewsConfigFile = rootProject.layout.projectDirectory.file(
        providers.provider { extensions.getByType<BuildParametersExtension>().config },
    )

    onVariants { variant ->
        val applicationIdProvider = if (variant is ApplicationVariant) {
            variant.applicationId
        } else {
            providers.provider { "" }
        }

        val firebaseOptionsProvider = providers.of(LocalFirebaseOptionsValueSource::class) {
            parameters {
                applicationId.set(applicationIdProvider)
                configFilePath.set(pixnewsConfigFile)
            }
        }

        @Suppress("GENERIC_VARIABLE_WRONG_DECLARATION")
        val firebaseOptionsTaskProvider = project.tasks.register<GenerateFirebaseOptionsTask>(
            "${variant.name}GenerateFirebaseOptions",
        ) {
            group = "Build"
            firebaseConfig.set(firebaseOptionsProvider)
            sourceOutputDir.set(layout.buildDirectory.dir("firebase-options"))
        }

        variant.sources.java?.addGeneratedSourceDirectory(
            taskProvider = firebaseOptionsTaskProvider,
            wiredWith = GenerateFirebaseOptionsTask::sourceOutputDir,
        )

        // Manually add google_app_id for Firebase Analytics
        // Cannot use put() here: https://github.com/gradle/gradle/issues/13364
        val googleAppIdKey = variant.makeResValueKey("string", "google_app_id")
        variant.resValues.putAll(
            firebaseOptionsProvider
                .map(ApplicationIdToMapOfValuesTransformer(googleAppIdKey))
                .orElse(emptyMap()),
        )
    }
}

internal class ApplicationIdToMapOfValuesTransformer(
    private val googleApiKey: ResValue.Key,
) : Transformer<Map<ResValue.Key, ResValue>, LocalFirebaseOptions> {
    override fun transform(options: LocalFirebaseOptions): Map<ResValue.Key, ResValue> {
        return options.applicationId?.let {
            mapOf(googleApiKey to ResValue(it))
        } ?: emptyMap()
    }
}

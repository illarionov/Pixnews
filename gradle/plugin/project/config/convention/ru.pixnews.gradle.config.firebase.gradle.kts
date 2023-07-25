/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import buildparameters.BuildParametersExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.ResValue
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.TestPlugin
import ru.pixnews.gradle.config.firebase.FirebaseConfigReader
import ru.pixnews.gradle.config.firebase.GenerateFirebaseOptionsTask

/**
 * Convention plugin that generates GeneratedFirebaseOptions.firebaseOptions using values from
 * configuration file and prepares required resource values.
 */
listOf(
    AppPlugin::class.java,
    LibraryPlugin::class.java,
    DynamicFeaturePlugin::class.java,
    TestPlugin::class.java,
).forEach { agpPlugin ->
    project.plugins.withType(agpPlugin) {
        project.extensions.configure(AndroidComponentsExtension::class.java) {
            registerFirebaseOptionsTask()
        }
    }
}

fun AndroidComponentsExtension<*, *, *>.registerFirebaseOptionsTask() {
    val pixnewsConfigFileContent = providers.fileContents(
        rootProject.layout.projectDirectory.file(
            providers.provider { extensions.getByType<BuildParametersExtension>().config },
        ),
    )

    onVariants { variant ->
        val applicationIdProvider = if (variant is ApplicationVariant) {
            variant.applicationId
        } else {
            providers.provider { "" }
        }
        val firebaseOptionsProvider = pixnewsConfigFileContent.asText.flatMap { configFileText ->
            if (variant is ApplicationVariant) {
                variant.applicationId
            } else {
                providers.provider { "" }
            }

            applicationIdProvider.map { applicationId ->
                FirebaseConfigReader(
                    configFileText,
                    applicationId.ifEmpty { null },
                ).read()
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
        variant.resValues.putAll(
            firebaseOptionsProvider
                .map { options ->
                    options.applicationId?.let {
                        mapOf(variant.makeResValueKey("string", "google_app_id") to ResValue(it))
                    } ?: emptyMap()
                }
                .orElse(emptyMap()),
        )
    }
}

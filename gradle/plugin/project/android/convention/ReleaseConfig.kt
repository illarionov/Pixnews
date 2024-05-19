/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("NULLABLE_PROPERTY_TYPE")

package ru.pixnews.gradle.project.android

import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ProviderFactory
import ru.pixnews.gradle.project.base.buildParameters
import java.io.StringReader
import java.util.Properties

internal class ReleaseConfig private constructor(
    propertiesFile: RegularFile,
    private val buildParameters: buildparameters.Signing,
    providers: ProviderFactory,
) {
    private val propertiesProvider = providers.fileContents(propertiesFile)
        .asText
        .map {
            Properties().apply {
                load(StringReader(it))
            }
        }
    private val properties: Properties? by lazy { propertiesProvider.orNull }

    val useReleaseKeystore: Boolean
        get() = !buildParameters.sign_with_debug_keys && properties != null

    val signingConfig: SigningConfig?
        get() = properties?.let {
            SigningConfig(
                storeFile = it.getProperty("signing_store_file") ?: error("Keystore file not defined"),
                keyAlias = it.getProperty("signing_key_alias"),
                storePassword = it.getProperty("signing_store_password"),
                keyPassword = it.getProperty("signing_key_password"),
            )
        }

    class SigningConfig(
        val storeFile: String,
        val keyAlias: String?,
        val storePassword: String?,
        val keyPassword: String?,
    )

    companion object {
        operator fun invoke(
            project: Project,
        ): ReleaseConfig {
            val signingParameters = project.buildParameters.signing
            val keystorePropertiesFilePath = signingParameters.release_keystore_properties_file
            val layout = project.rootProject.layout
            return ReleaseConfig(
                propertiesFile = layout.projectDirectory.file(keystorePropertiesFilePath),
                buildParameters = signingParameters,
                providers = project.providers,
            )
        }
    }
}

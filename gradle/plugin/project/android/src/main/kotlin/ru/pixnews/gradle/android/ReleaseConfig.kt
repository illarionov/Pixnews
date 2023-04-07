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
package ru.pixnews.gradle.android

import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ProviderFactory
import ru.pixnews.gradle.base.buildParameters
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
    val properties: Properties? by lazy { propertiesProvider.orNull }

    val useReleaseKeystore: Boolean
        get() = !buildParameters.sign_with_debug_keys && properties != null

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

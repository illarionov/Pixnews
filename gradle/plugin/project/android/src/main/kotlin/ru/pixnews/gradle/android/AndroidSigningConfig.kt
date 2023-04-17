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

import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.SigningConfig
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

internal fun Project.configureSigning(
    signingConfigsContainer: NamedDomainObjectContainer<out ApkSigningConfig>,
    config: ReleaseConfig = ReleaseConfig(this),
) {
    with(signingConfigsContainer) {
        getByName("debug") {
            configureDebugSigningConfig(this)
        }

        if (config.useReleaseKeystore) {
            config.signingConfig?.let { signConfig ->
                create("release") {
                    storeFile = rootProject.file(signConfig.storeFile)
                    keyAlias = signConfig.keyAlias
                    storePassword = signConfig.storePassword
                    keyPassword = signConfig.keyPassword
                }
            }
        }
    }
}

internal fun Project.configureDebugSigningConfig(config: SigningConfig) = with(config) {
    storeFile = rootProject.file("config/signing/debug.jks")
    storePassword = "android"
    keyAlias = "androiddebugkey"
    keyPassword = "android"
}

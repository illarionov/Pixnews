/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.android

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
                    storeFile = layout.settingsDirectory.file(signConfig.storeFile).asFile
                    keyAlias = signConfig.keyAlias
                    storePassword = signConfig.storePassword
                    keyPassword = signConfig.keyPassword
                }
            }
        }
    }
}

internal fun Project.configureDebugSigningConfig(config: SigningConfig) = with(config) {
    storeFile = layout.settingsDirectory.file("config/signing/debug.jks").asFile
    storePassword = "android"
    keyAlias = "androiddebugkey"
    keyPassword = "android"
}

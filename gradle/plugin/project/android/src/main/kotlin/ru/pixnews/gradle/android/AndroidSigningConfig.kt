/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

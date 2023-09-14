/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

internal fun FirebaseRemoteConfigValue.sourceAsString(): String {
    return when (this.source) {
        FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT -> "default"
        FirebaseRemoteConfig.VALUE_SOURCE_REMOTE -> "remote"
        FirebaseRemoteConfig.VALUE_SOURCE_STATIC -> "static default"
        else -> "source($this)"
    }
}

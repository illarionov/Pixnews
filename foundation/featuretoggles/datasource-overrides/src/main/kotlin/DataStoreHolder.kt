/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.overrides

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

internal val Context.overridesDataStore: DataStore<Overrides> by dataStore(
    fileName = "experiment_overrides.pb",
    serializer = OverridesSerializer,
)

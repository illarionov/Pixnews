/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.firebase

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import ru.pixnews.gradle.config.firebase.LocalFirebaseOptionsValueSource.Parameters
import ru.pixnews.gradle.config.util.toProperties

abstract class LocalFirebaseOptionsValueSource : ValueSource<LocalFirebaseOptions, Parameters> {
    override fun obtain(): LocalFirebaseOptions? {
        val configProperties = parameters.configFilePath.get().asFile.toProperties()
        return FirebaseConfigReader(
            configProperties,
            parameters.applicationId.get().ifEmpty { null },
        ).read()
    }

    interface Parameters : ValueSourceParameters {
        val applicationId: Property<String>
        val configFilePath: RegularFileProperty
    }
}

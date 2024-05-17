/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.config.igdb

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import ru.pixnews.gradle.project.config.igdb.LocalIgdbOptionsValueSource.Parameters
import ru.pixnews.gradle.project.config.util.toProperties

abstract class LocalIgdbOptionsValueSource : ValueSource<LocalIgdbOptions, Parameters> {
    override fun obtain(): LocalIgdbOptions? {
        val configProperties = parameters.configFilePath.get().asFile.toProperties()
        return IgdbConfigReader(configProperties).read()
    }

    interface Parameters : ValueSourceParameters {
        val configFilePath: RegularFileProperty
    }
}

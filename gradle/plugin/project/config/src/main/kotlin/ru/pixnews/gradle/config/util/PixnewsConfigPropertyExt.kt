/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.util

import org.gradle.api.provider.Property
import org.slf4j.Logger

fun <T> Property<T>.getWarnIfNotPresent(
    logger: Logger,
    name: String,
    ifNotPresent: () -> T,
): T {
    return if (isPresent) {
        get()
    } else {
        logger.warn(
            """
                Configuration file is not installed. Will use the default $name values.
                Please copy config/pixnews.properties.sample to config/pixnews.properties and set the required
                configuration parameters.
            """.trimIndent(),
        )
        ifNotPresent()
    }
}

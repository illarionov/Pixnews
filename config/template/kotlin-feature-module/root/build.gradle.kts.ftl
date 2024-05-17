/*
 * Copyright (c) ${.now?string.yyyy}, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.gradle.project.kotlin.library")
}

pixnews {
    compose.set(${enableCompose?string('true', 'false')})
}

group = "${packageName}"

dependencies {
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.gradle.base.versionCatalog

plugins {
    id("com.squareup.wire")
}

dependencies {
    add("implementation", versionCatalog.findLibrary("wire.runtime.jvm").orElseThrow())
}

wire {
    kotlin {
        javaInterop = false
    }
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.gradle.base.versionCatalog

/**
 * Convention plugin that configures anvil with generateDaggerFactories turned on
 */
plugins {
    id("com.squareup.anvil")
}

anvil {
    generateDaggerFactories.set(true)
}

dependencies {
    add("anvil", project(":foundation:di:anvil-codegen"))
    add("api", versionCatalog.findLibrary("dagger").orElseThrow())
}

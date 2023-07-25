/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import org.gradle.kotlin.dsl.support.expectedKotlinDslPluginsVersion

plugins {
    `kotlin-dsl`
}

group = "ru.pixnews.gradle.base"

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:$expectedKotlinDslPluginsVersion")
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.pixnews.gradle"

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging {
        events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED)
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
    implementation(libs.wire.plugin)
    api(libs.wire.schema)
    implementation(libs.kotlinpoet) {
        exclude(module = "kotlin-reflect")
    }

    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.compile.testing)
    testImplementation(kotlin("scripting-compiler"))
}

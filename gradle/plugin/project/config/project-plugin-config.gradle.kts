/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("ru.pixnews.gradle.base.kotlindsl")
}

group = "ru.pixnews.gradle.config"

kotlin {
    jvmToolchain(17)
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging {
        events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED)
    }
}

dependencies {
    implementation(platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
    implementation("ru.pixnews.gradle.base:gradle-build-parameters")
    implementation(projects.base)
    implementation(libs.agp.plugin)
    implementation(libs.kotlin.jvm.plugin)
    implementation(libs.kotlinpoet)

    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.compile.testing)
}

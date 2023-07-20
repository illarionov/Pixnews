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

import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import ru.pixnews.gradle.base.createPixnewsExtension
import ru.pixnews.gradle.base.pixnews
import ru.pixnews.gradle.testing.configureCommonUnitTesting
import ru.pixnews.gradle.testing.configureCommonUnitTestingOptions

/**
 * Convention plugin for use in kotlin only modules
 */
plugins {
    kotlin("jvm")
    id("ru.pixnews.gradle.base.build-parameters")
    // TODO Uncomment when circular dependency will be fixed: :generateJvmLintModel
    // id("ru.pixnews.gradle.lint.android-lint-noagp")
}

createPixnewsExtension()

kotlin {
    explicitApi = ExplicitApiMode.Warning
    sourceSets {
        main {
            kotlin.setSrcDirs(listOf("main"))
            resources.setSrcDirs(listOf("main_resources"))
        }
        test {
            kotlin.setSrcDirs(listOf("test"))
            resources.setSrcDirs(listOf("test_resources"))
        }
    }
}

plugins.withId("java-test-fixtures") {
    kotlin.sourceSets.getByName("testFixtures") {
        kotlin.setSrcDirs(listOf("testFixtures"))
        resources.setSrcDirs(listOf("testFixtures_resources"))
    }
    dependencies {
        add("testFixturesImplementation", platform("ru.pixnews.gradle.base:gradle-billofmaterials"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<KotlinJvmCompilerOptions>>()
    .configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            // D8: An error occurred when parsing kotlin metadata.
            // languageVersion.set(KOTLIN_2_0)
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
                "-Xjvm-default=all",
            )
            if (this@configureEach.name.endsWith("TestFixturesKotlin")) {
                freeCompilerArgs.addAll("-Xexplicit-api=warning")
            }
        }
    }

dependencies {
    val bom = platform("ru.pixnews.gradle.base:gradle-billofmaterials")
    listOf("implementation", "testImplementation").forEach {
        add(it, bom)
    }
}

afterEvaluate {
    val unitTestEngine = project.pixnews.unitTestEngine.get()
    configureCommonUnitTesting(unitTestEngine)
    tasks.withType<Test>().configureEach {
        configureCommonUnitTestingOptions(unitTestEngine)
    }
}

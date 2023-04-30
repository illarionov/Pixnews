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
package ru.pixnews.gradle.android.agp.workarounds

import com.android.build.gradle.internal.lint.AndroidLintTask
import com.android.build.gradle.internal.tasks.ManagedDeviceInstrumentationTestTask
import com.android.build.gradle.internal.tasks.R8Task
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

object AndroidGradlePluginWorkarounds {
    fun Project.disableProjectAsExternalDependencyLintTaskWarning() {
        tasks.withType<AndroidLintTask>().configureEach {
            this.variantInputs.mainArtifact.warnIfProjectTreatedAsExternalDependency.set(false)
        }
    }

    // Workaround for https://issuetracker.google.com/issues/279516901
    fun Project.disableArtProfileRewriting() {
        afterEvaluate {
            tasks.withType<R8Task>().configureEach {
                artProfileRewriting.set(false)
            }
        }
    }

    // https://issuetracker.google.com/issues/262270582
    fun Project.initializeManagedDeviceTestEnvironment() {
        tasks.withType<ManagedDeviceInstrumentationTestTask>().configureEach {
            doFirst {
                com.android.utils.Environment.initialize()
            }
        }
    }
}

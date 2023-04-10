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
package ru.pixnews.foundation.featuretoggles.inject

import kotlin.annotation.AnnotationTarget.CLASS

/**
 * Annotate a Experiment class with this to automatically contribute it to the ExperimentScope multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 *  @Module
 *  @ContributesTo(ExperimentScope::class)
 *  abstract class ExperimentModule {
 *    @Binds @IntoSet
 *    abstract fun bindMainExperiment(experiment: MainExperiment): Experiment
 *  }
 *```
 * The generated code created via the anvil-codegen module.
 */
@Target(CLASS)
public annotation class ContributesExperiment

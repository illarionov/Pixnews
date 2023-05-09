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
package ru.pixnews.foundation.initializers.inject

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass

/**
 * Annotate a Initializer / AsyncInitializer class with this to automatically contribute it to the
 * Initializer multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 *  @Module
 *  @ContributesTo(AppInitializersScope::class)
 *  abstract class ExperimentModule {
 *    @Binds @IntoSet
 *    abstract fun bindMainExperiment(initializer: MainInitializer): Initializer
 *  }
 *```
 * The generated code created via the anvil-codegen module.
 */
@Target(CLASS)
public annotation class ContributesInitializer(
    /**
     * This contributed module will replace these contributed classes. The array is allowed to
     * include other contributed bindings, multibindings and Dagger modules. All replaced classes
     * must use the same scope.
     */
    val replaces: Array<KClass<*>> = [],
)

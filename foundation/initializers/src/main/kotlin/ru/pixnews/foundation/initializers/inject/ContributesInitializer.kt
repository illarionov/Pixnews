/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

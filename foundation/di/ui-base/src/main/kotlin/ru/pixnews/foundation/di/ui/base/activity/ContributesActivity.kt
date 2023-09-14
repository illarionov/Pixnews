/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.activity

/**
 * Annotate a Activity class with this to automatically contribute it to the ActivityScope multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 *  @Module
 *  @ContributesTo(ActivityScope::class)
 *  abstract class MainActivityModule {
 *    @Binds
 *    @IntoMap
 *    @ActivityKey(MainActivity::class)
 *    @SingleIn(ActivityScope::class)
 *    abstract fun bindsMainInjector(target: MembersInjector<MainActivity>): MembersInjector<out Activity>
 *  }
 *```
 * The generated code created via the anvil-codegen module.
 */
public annotation class ContributesActivity

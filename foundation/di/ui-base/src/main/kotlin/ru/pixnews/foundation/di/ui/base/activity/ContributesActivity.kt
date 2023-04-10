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

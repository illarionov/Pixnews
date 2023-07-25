/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.instrumented.test.di

/**
 * Annotate a test class with this to automatically contribute it to the AppScope multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 *  @Module
 *  @ContributesTo(AppScope::class)
 *  object TestModule {
 *    @Provides
 *    @IntoMap
 *    @ClassKey(MainTest::class)
 *    @SingleIn(AppScope::class)
 *    fun providesFirstScreenTest(injector: MembersInjector<MainTest>): SingleInstrumentedTestInjector {
 *      return SingleInstrumentedTestInjector(injector)
 *    }
 *```
 * The generated code created via the anvil-codegen module.
 */
public annotation class ContributesTest

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.instrumented.test.di

internal class DefaultInstrumentedTestInjector(
    private val providers: Map<Class<out Any>, SingleInstrumentedTestInjector>,
) : InstrumentedTestInjector {
    override fun inject(test: Any) {
        providers[test::class.java]?.also {
            it.injectMembers(test)
        } ?: error("No member injector for $test")
    }
}

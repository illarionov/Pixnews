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
package ru.pixnews.foundation.di.instrumented.test

internal class DefaultInstrumentedTestInjector(
    private val providers: Map<Class<out Any>, SingleInstrumentedTestInjector>,
) : InstrumentedTestInjector {
    @Suppress("UNCHECKED_CAST")
    override fun inject(test: Any) {
        providers[test::class.java]?.also {
            it.injectMembers(test)
        } ?: error("No member injector for $test")
    }
}

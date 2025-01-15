/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.instrumented.test.di

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import ru.pixnews.anvil.ksp.codegen.test.inject.wiring.DefaultInstrumentedTestInjector
import ru.pixnews.anvil.ksp.codegen.test.inject.wiring.InstrumentedTestInjector
import ru.pixnews.anvil.ksp.codegen.test.inject.wiring.SingleInstrumentedTestInjector
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
@RestrictTo(LIBRARY)
public interface InstrumentedTestsInjectorsModule {
    @Multibinds
    public fun instrumentedTestInjectors(): DaggerMap<Class<out Any>, SingleInstrumentedTestInjector>

    public companion object {
        @Reusable
        @Provides
        public fun provideInstrumentedTestInjector(
            injectors: DaggerMap<Class<out Any>, SingleInstrumentedTestInjector>,
        ): InstrumentedTestInjector {
            return DefaultInstrumentedTestInjector(injectors)
        }
    }
}

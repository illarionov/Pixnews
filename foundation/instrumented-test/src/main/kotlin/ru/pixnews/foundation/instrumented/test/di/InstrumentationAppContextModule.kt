/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.instrumented.test.di

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
@RestrictTo(LIBRARY)
public object InstrumentationAppContextModule {
    @Provides
    @InstrumentationAppContext
    public fun providesInstrumentationContext(): Context {
        return InstrumentationRegistry.getInstrumentation().context
    }
}

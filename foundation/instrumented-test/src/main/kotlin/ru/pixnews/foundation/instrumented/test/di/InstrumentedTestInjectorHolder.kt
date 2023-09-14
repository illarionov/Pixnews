/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.instrumented.test.di

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import com.squareup.anvil.annotations.ContributesTo
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@RestrictTo(LIBRARY)
public interface InstrumentedTestInjectorHolder {
    public val instrumentedTestInjector: InstrumentedTestInjector
}

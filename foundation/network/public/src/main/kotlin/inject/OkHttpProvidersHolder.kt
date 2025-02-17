/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network.inject

import androidx.annotation.RestrictTo
import com.squareup.anvil.annotations.ContributesTo
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.network.OkHttpClientProvider
import ru.pixnews.foundation.network.RootOkHttpClientProvider

@ContributesTo(AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface OkHttpProvidersHolder {
    public val rootOkHttpClientProvider: RootOkHttpClientProvider
    public val okHttpClientProvider: OkHttpClientProvider
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import com.squareup.anvil.annotations.ContributesTo
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.inject.data.MockObserveUpcomingReleasesByDateUseCase
import ru.pixnews.test.app.mock.NetworkBehavior

@ContributesTo(AppScope::class)
@RestrictTo(LIBRARY)
interface MockResourcesHolder {
    val mockUseCase: MockObserveUpcomingReleasesByDateUseCase
    val networkBehavior: NetworkBehavior
}

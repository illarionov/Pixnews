/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
interface SystemTimeModule {
    @Binds
    fun bindsClock(clock: Clock.System): Clock

    companion object {
        @Provides
        fun provideTimezone(): Function0<TimeZone> = TimeZone.Companion::currentSystemDefault
    }
}

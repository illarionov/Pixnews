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
@file:Suppress("MaxLineLength")

package ru.pixnews.features

import com.squareup.anvil.annotations.ContributesTo
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.pixnews.features.calendar.CalendarFeedWidthOnMediumSizeTest
import ru.pixnews.features.calendar.CalendarScreenPaddingsTest
import ru.pixnews.features.root.FirstScreenTest
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.di.instrumented.testing.SingleInstrumentedTestInjector

@Module
@ContributesTo(AppScope::class)
public object TestModule {
    @Provides
    @IntoMap
    @ClassKey(FirstScreenTest::class)
    @SingleIn(AppScope::class)
    fun providesFirstScreenTest(injector: MembersInjector<FirstScreenTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }

    @Provides
    @IntoMap
    @ClassKey(CalendarScreenPaddingsTest::class)
    @SingleIn(AppScope::class)
    fun providesCalendarScreenPaddingsTest(injector: MembersInjector<CalendarScreenPaddingsTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }

    @Provides
    @IntoMap
    @ClassKey(CalendarFeedWidthOnMediumSizeTest::class)
    @SingleIn(AppScope::class)
    fun providesCalendarFeedWidthOnMediumSizeTest(injector: MembersInjector<CalendarFeedWidthOnMediumSizeTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }
}

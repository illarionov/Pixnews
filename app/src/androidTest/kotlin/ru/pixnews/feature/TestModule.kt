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

package ru.pixnews.feature

import com.squareup.anvil.annotations.ContributesTo
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.pixnews.feature.calendar.CalendarFeedWidthOnMediumSizeTest
import ru.pixnews.feature.calendar.CalendarScreenFeedPaddingsTest
import ru.pixnews.feature.calendar.CalendarScreenVerticalPaddingsTest
import ru.pixnews.feature.root.FirstScreenTest
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.di.instrumented.test.SingleInstrumentedTestInjector

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
    @ClassKey(CalendarScreenVerticalPaddingsTest::class)
    @SingleIn(AppScope::class)
    fun providesCalendarScreenPaddingsTest(injector: MembersInjector<CalendarScreenVerticalPaddingsTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }

    @Provides
    @IntoMap
    @ClassKey(CalendarFeedWidthOnMediumSizeTest::class)
    @SingleIn(AppScope::class)
    fun providesCalendarFeedWidthOnMediumSizeTest(injector: MembersInjector<CalendarFeedWidthOnMediumSizeTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }

    @Provides
    @IntoMap
    @ClassKey(CalendarScreenFeedPaddingsTest::class)
    @SingleIn(AppScope::class)
    fun providesCalendarScreenPaddingTest(injector: MembersInjector<CalendarScreenFeedPaddingsTest>): SingleInstrumentedTestInjector {
        return SingleInstrumentedTestInjector(injector)
    }
}
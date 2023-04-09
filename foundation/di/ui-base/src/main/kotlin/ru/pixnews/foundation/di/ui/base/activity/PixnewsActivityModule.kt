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
package ru.pixnews.foundation.di.ui.base.activity

import android.app.Activity
import androidx.annotation.RestrictTo
import com.squareup.anvil.annotations.ContributesTo
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.di.base.DaggerMap

@Module
@ContributesTo(ActivityScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface PixnewsActivityModule {
    @Multibinds
    public fun activityInjectors(): DaggerMap<Class<out Activity>, MembersInjector<out Activity>>

    public companion object {
        @Reusable
        @Provides
        public fun provideActivityInjector(
            injectors: DaggerMap<Class<out Activity>, MembersInjector<out Activity>>,
        ): ActivityInjector {
            return DefaultActivityInjector(injectors)
        }
    }
}

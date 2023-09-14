/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

@ContributesTo(ActivityScope::class)
@Module
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

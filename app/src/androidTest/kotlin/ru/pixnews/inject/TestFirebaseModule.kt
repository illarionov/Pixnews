/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.inject.FirebaseModule.FirebaseProviderHolder

@ContributesTo(AppScope::class, replaces = [FirebaseModule::class])
@Module
object TestFirebaseModule

@ContributesTo(AppScope::class, replaces = [FirebaseProviderHolder::class])
public interface FirebaseProviderHolder

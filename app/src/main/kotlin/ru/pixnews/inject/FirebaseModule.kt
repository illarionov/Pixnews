/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import android.content.Context
import com.google.firebase.FirebaseApp
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.firebase.GeneratedFirebaseOptions
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
public object FirebaseModule {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideFirebaseApp(@ApplicationContext context: Context): FirebaseApp {
        return FirebaseApp.initializeApp(context, GeneratedFirebaseOptions.firebaseOptions)
    }

    @ContributesTo(AppScope::class)
    public interface FirebaseProviderHolder {
        fun getFirebaseAppProvider(): FirebaseApp
    }
}

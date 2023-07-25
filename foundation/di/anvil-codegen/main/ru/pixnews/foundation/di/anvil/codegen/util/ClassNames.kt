/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import dagger.Binds
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.assisted.AssistedFactory
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn

internal object ClassNames {
    val applicationContext = ApplicationContext::class.asClassName()
    val singleIn = SingleIn::class.asClassName()
    val activityMapKey = ClassName("ru.pixnews.foundation.di.ui.base.activity", "ActivityMapKey")
    val coroutineWorkerMapKey = ClassName("ru.pixnews.foundation.di.workmanager", "CoroutineWorkerMapKey")
    val activityScope = ClassName("ru.pixnews.foundation.di.ui.base.activity", "ActivityScope")
    val appInitializersScope = ClassName("ru.pixnews.foundation.initializers.inject", "AppInitializersScope")
    val appScope = AppScope::class.asClassName()
    val coroutineWorkerFactory = ClassName("ru.pixnews.foundation.di.workmanager", "CoroutineWorkerFactory")
    val workManagerScope: ClassName = ClassName("ru.pixnews.foundation.di.workmanager", "WorkManagerScope")
    val experiment = ClassName("ru.pixnews.foundation.featuretoggles", "Experiment")
    val experimentScope = ClassName("ru.pixnews.foundation.featuretoggles.inject", "ExperimentScope")
    val experimentVariantMapKey = ClassName("ru.pixnews.foundation.featuretoggles.inject", "ExperimentVariantMapKey")
    val experimentVariantSerializer = ClassName("ru.pixnews.foundation.featuretoggles", "ExperimentVariantSerializer")
    val singleInstrumentedTestInjector = ClassName(
        packageName = "ru.pixnews.foundation.instrumented.test.di",
        "SingleInstrumentedTestInjector",
    )
    val viewModelFactory = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelFactory")
    val viewModelMapKey = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelMapKey")
    val viewModelScope = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelScope")
    val asyncInitializer = ClassName("ru.pixnews.foundation.initializers", "AsyncInitializer")
    val initializer = ClassName("ru.pixnews.foundation.initializers", "Initializer")

    internal object Anvil {
        val contributesMultibinding = ContributesMultibinding::class.asClassName()
        val contributesTo = ContributesTo::class.asClassName()
    }

    internal object Dagger {
        val assistedFactory = AssistedFactory::class.asClassName()
        val binds = Binds::class.asClassName()
        val classKey = ClassKey::class.asClassName()
        val intoMap = IntoMap::class.asClassName()
        val intoSet = IntoSet::class.asClassName()
        val membersInjector = MembersInjector::class.asClassName()
        val module = Module::class.asClassName()
        val provides = Provides::class.asClassName()
        val reusable = Reusable::class.asClassName()
    }

    internal object Android {
        val activity = ClassName("android.app", "Activity")
        val context = ClassName("android.content", "Context")
        val savedStateHandle = ClassName("androidx.lifecycle", "SavedStateHandle")
        val workerParameters = ClassName("androidx.work", "WorkerParameters")
    }
}

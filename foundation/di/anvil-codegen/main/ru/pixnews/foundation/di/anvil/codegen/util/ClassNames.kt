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
package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import dagger.Binds
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn

internal object ClassNames {
    val singleIn = SingleIn::class.asClassName()
    val activityMapKey = ClassName("ru.pixnews.foundation.di.ui.base.activity", "ActivityMapKey")
    val activityScope = ClassName("ru.pixnews.foundation.di.ui.base.activity", "ActivityScope")
    val appScope = AppScope::class.asClassName()
    val experiment = ClassName("ru.pixnews.foundation.featuretoggles", "Experiment")
    val experimentScope = ClassName("ru.pixnews.foundation.featuretoggles.inject", "ExperimentScope")
    val experimentVariantMapKey = ClassName("ru.pixnews.foundation.featuretoggles.inject", "ExperimentVariantMapKey")
    val experimentVariantSerializer = ClassName("ru.pixnews.foundation.featuretoggles", "ExperimentVariantSerializer")
    val singleInstrumentedTestInjector = ClassName(
        packageName = "ru.pixnews.foundation.di.instrumented.test",
        "SingleInstrumentedTestInjector",
    )
    val viewModelFactory = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelFactory")
    val viewModelMapKey = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelMapKey")
    val viewModelScope = ClassName("ru.pixnews.foundation.di.ui.base.viewmodel", "ViewModelScope")

    internal object Anvil {
        val contributesTo = ContributesTo::class.asClassName()
    }

    internal object Dagger {
        val binds = Binds::class.asClassName()
        val classKey = ClassKey::class.asClassName()
        val intoMap = IntoMap::class.asClassName()
        val intoSet = IntoSet::class.asClassName()
        val membersInjector = MembersInjector::class.asClassName()
        val module = Module::class.asClassName()
        val provides = Provides::class.asClassName()
    }

    internal object Android {
        val activity = ClassName("android.app", "Activity")
        val savedStateHandle = ClassName("androidx.lifecycle", "SavedStateHandle")
    }
}

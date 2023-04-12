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

import com.squareup.anvil.compiler.internal.fqName
import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName

internal object FqNames {
    val anyClass: FqName = Any::class.fqName
    val contributesExperiment = FqName("ru.pixnews.foundation.featuretoggles.inject.ContributesExperiment")
    val contributesVariantSerializer = FqName(
        "ru.pixnews.foundation.featuretoggles.inject.ContributesExperimentVariantSerializer",
    )
    val contributesActivity = FqName("ru.pixnews.foundation.di.ui.base.activity.ContributesActivity")
    val contributesViewModel = FqName("ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel")
    val contributesTest = FqName("ru.pixnews.foundation.di.instrumented.test.ContributesTest")
    val experiment = ClassNames.experiment.asFqName()
    val experimentVariantSerializer = ClassNames.experimentVariantSerializer.asFqName()

    internal object Android {
        val activity = ClassNames.Android.activity.asFqName()
        val viewModel = FqName("androidx.lifecycle.ViewModel")
    }

    private fun ClassName.asFqName(): FqName = FqName(this.canonicalName)
}

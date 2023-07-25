/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName

internal object FqNames {
    val contributesActivity = FqName("ru.pixnews.foundation.di.ui.base.activity.ContributesActivity")
    val contributesCoroutineWorker = FqName("ru.pixnews.foundation.di.workmanager.ContributesCoroutineWorker")
    val contributesExperiment = FqName("ru.pixnews.foundation.featuretoggles.inject.ContributesExperiment")
    val contributesInitializer = FqName("ru.pixnews.foundation.initializers.inject.ContributesInitializer")
    val contributesTest = FqName("ru.pixnews.foundation.instrumented.test.di.ContributesTest")
    val contributesVariantSerializer = FqName(
        "ru.pixnews.foundation.featuretoggles.inject.ContributesExperimentVariantSerializer",
    )
    val contributesViewModel = FqName("ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel")
    val experiment = ClassNames.experiment.asFqName()
    val experimentVariantSerializer = ClassNames.experimentVariantSerializer.asFqName()

    internal object Android {
        val activity = ClassNames.Android.activity.asFqName()
        val coroutineWorker = FqName("androidx.work.CoroutineWorker")
        val viewModel = FqName("androidx.lifecycle.ViewModel")
    }

    private fun ClassName.asFqName(): FqName = FqName(this.canonicalName)
}

/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.ParameterReference
import com.squareup.kotlinpoet.TypeName
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames.Android

internal class ConstructorParameter(
    val name: String,
    val resolvedType: TypeName,
)

internal fun ConstructorParameter.isSavedStateHandle(): Boolean = resolvedType == Android.savedStateHandle

internal fun List<ParameterReference>.parseConstructorParameters(
    implementingClass: ClassReference,
): List<ConstructorParameter> = this.map {
    ConstructorParameter(it.name, it.resolveTypeName(implementingClass))
}

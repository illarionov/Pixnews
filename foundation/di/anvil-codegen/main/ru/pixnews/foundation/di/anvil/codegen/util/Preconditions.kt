/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.anvil.compiler.internal.reference.AnvilCompilationExceptionClassReference
import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.allSuperTypeClassReferences
import org.jetbrains.kotlin.name.FqName

internal fun ClassReference.checkClassExtendsType(type: FqName) {
    if (allSuperTypeClassReferences().none { it.fqName == type }) {
        throw AnvilCompilationExceptionClassReference(
            message = "${this.fqName} doesn't extend $type",
            classReference = this,
        )
    }
}

internal fun ClassReference.checkClassExtendsAnyOfType(
    vararg types: FqName,
) {
    if (allSuperTypeClassReferences().none { it.fqName in types }) {
        throw AnvilCompilationExceptionClassReference(
            message = "${this.fqName} doesn't extend any od $types",
            classReference = this,
        )
    }
}

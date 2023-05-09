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

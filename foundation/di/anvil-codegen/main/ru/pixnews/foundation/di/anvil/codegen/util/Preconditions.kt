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

// com/squareup/anvil/compiler/codegen/ClassReferenceExtensions.kt
internal fun ClassReference.checkClassExtendsBoundType(
    annotationFqName: FqName,
) {
    val boundType = annotations.find { it.fqName == annotationFqName }?.boundTypeOrNull()
        ?: directSuperTypeReferences().singleOrNull()?.asClassReference()
        ?: throw AnvilCompilationExceptionClassReference(
            message = "Couldn't find the bound type.",
            classReference = this,
        )

    // The boundType is declared explicitly in the annotation. Since all classes extend Any, we can
    // stop here.
    if (boundType.fqName == FqNames.anyClass) {
        return
    }

    if (allSuperTypeClassReferences().none { it.fqName == boundType.fqName }) {
        throw AnvilCompilationExceptionClassReference(
            message = "${this.fqName} contributes a binding for ${boundType.fqName}, but doesn't " +
                    "extend this type.",
            classReference = this,
        )
    }
}

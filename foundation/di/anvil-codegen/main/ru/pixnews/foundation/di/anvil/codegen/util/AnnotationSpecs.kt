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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

/**
 * `@ContributesTo(className::class, replaces = [..])`
 */
internal fun contributesToAnnotation(
    className: ClassName,
    replaces: List<TypeName> = emptyList(),
): AnnotationSpec {
    return with(AnnotationSpec.builder(ClassNames.Anvil.contributesTo)) {
        addMember("%T::class", className)
        if (replaces.isNotEmpty()) {
            @Suppress("SpreadOperator")
            addMember(
                "replaces = [${replaces.joinToString(",") { "%T::class" }}]",
                *replaces.toTypedArray(),
            )
        }
        build()
    }
}

/**
 * `@ContributesTo(className::class)`
 */
internal fun contributesMultibindingAnnotation(scope: ClassName): AnnotationSpec {
    return AnnotationSpec.builder(ClassNames.Anvil.contributesMultibinding)
        .addMember("scope = %T::class", scope)
        .build()
}

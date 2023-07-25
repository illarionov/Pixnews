/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

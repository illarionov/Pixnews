/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.anvil.codegen.util

import com.squareup.kotlinpoet.ClassName

fun ClassLoader.loadClass(clazz: ClassName): Class<*> = this.loadClass(clazz.canonicalName)

@Suppress("UNCHECKED_CAST")
public fun <T> Annotation.getElementValue(elementName: String): T =
    this::class.java.declaredMethods.single { it.name == elementName }.invoke(this) as T

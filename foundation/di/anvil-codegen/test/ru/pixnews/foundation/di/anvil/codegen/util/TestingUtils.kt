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

import com.squareup.kotlinpoet.ClassName

fun ClassLoader.loadClass(clazz: ClassName): Class<*> = this.loadClass(clazz.canonicalName)

@Suppress("UNCHECKED_CAST")
public fun <T> Annotation.getElementValue(elementName: String): T =
    this::class.java.declaredMethods.single { it.name == elementName }.invoke(this) as T
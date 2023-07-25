/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.base.scopes

import javax.inject.Scope
import kotlin.reflect.KClass

/**
 * @property clazz
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
public annotation class SingleIn(val clazz: KClass<*>)

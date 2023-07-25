/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.functional

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right

public typealias Result<E, T> = Either<E, T>

public inline fun <E : Any, T : Any> Result<E, T>.onSuccess(action: (left: T) -> Unit): Result<E, T> =
    this.onRight(action)

public inline fun <E : Any, T : Any> Result<E, T>.onError(action: (error: E) -> Unit): Result<E, T> =
    this.onLeft(action)

public fun <A> A.completeFailure(): Result<A, Nothing> = Left(this)

public fun <A> A.completeSuccess(): Result<Nothing, A> = Right(this)

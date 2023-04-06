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

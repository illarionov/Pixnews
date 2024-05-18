/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.functional.network

import java.io.IOException

/**
 * Wrapper for the [NetworkRequestFailure] for cases when an error needs to be passed by exceptions
 */
public class NetworkRequestFailureException public constructor(
    public val failure: NetworkRequestFailure<*>,
    message: String? = null,
) : IOException(message)

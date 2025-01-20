/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb

import at.released.igdbclient.IgdbClient
import at.released.igdbclient.IgdbEndpoint
import at.released.igdbclient.IgdbResult
import at.released.igdbclient.IgdbWebhookApi
import at.released.igdbclient.apicalypse.ApicalypseQuery
import at.released.igdbclient.error.IgdbHttpErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class LazyIgdbClient(
    private val igdbClientFactory: () -> IgdbClient,
    private val backgroundDispatcher: CoroutineDispatcher,
) : IgdbClient {
    private var igdbClient: IgdbClient? = null
    private val igdbClientMutex: Mutex = Mutex()

    override val webhookApi: IgdbWebhookApi get() = error("Should not be called")

    override suspend fun <T : Any> execute(
        endpoint: IgdbEndpoint<T>,
        query: ApicalypseQuery,
    ): IgdbResult<T, IgdbHttpErrorResponse> = getIgdbClient().execute(endpoint, query)

    private suspend fun getIgdbClient(): IgdbClient {
        val client = igdbClientMutex.withLock { igdbClient }
        if (client != null) {
            return client
        }

        return withContext(backgroundDispatcher) {
            igdbClientMutex.withLock {
                igdbClient ?: igdbClientFactory().also { igdbClient = it }
            }
        }
    }
}
